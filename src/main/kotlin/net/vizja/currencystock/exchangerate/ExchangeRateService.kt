package net.vizja.currencystock.exchangerate

import com.neovisionaries.i18n.CurrencyCode
import mu.KLogging
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.time.LocalDate
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors
import java.util.function.Supplier

@Service
class ExchangeRateService(
        private val restTemplate: RestTemplate, private val repository: ExchangeRateRepository
) {

    private val executorService = Executors.newFixedThreadPool(3)

    @EventListener(ApplicationReadyEvent::class)
    fun loadExchangeRates() {
        repository.deleteAllInBatch()
        val uriA = "http://api.nbp.pl/api/exchangerates/tables/a?format=json"
        val uriB = "http://api.nbp.pl/api/exchangerates/tables/b?format=json"
        val uriC = "http://api.nbp.pl/api/exchangerates/tables/c?format=json"
        val tableCJob = CompletableFuture.supplyAsync(Supplier {
            val exchangeRatesC = restTemplate.getForEntity(uriC, Array<ExchangeRateCResponse>::class.java).body
            val exchangeRates = exchangeRatesC?.first()?.toDomain()
            exchangeRates?.let {
                repository.saveAll(exchangeRates)
            }
        }, executorService).exceptionally {
            logger.error { "Failed to parse C exchange rates:'$it'" }
            emptyList()
        }

        val tableAJob = CompletableFuture.supplyAsync(Supplier {
            val exchangeRatesA = restTemplate.getForEntity(uriA, Array<ExchangeRateABResponse>::class.java).body
            val exchangeRates = exchangeRatesA?.first()?.toDomain()
            exchangeRates?.let {
                repository.saveAll(exchangeRates)
            }
        }, executorService).exceptionally {
            logger.error { "Failed to parse A exchange rates:'$it'" }
            emptyList()
        }

        val tableBJob = CompletableFuture.supplyAsync(Supplier {
            val exchangeRatesB = restTemplate.getForEntity(uriB, Array<ExchangeRateABResponse>::class.java).body
            val exchangeRates = exchangeRatesB?.first()?.toDomain()
            exchangeRates?.let {
                repository.saveAll(exchangeRates)
            }
        }, executorService).exceptionally {
            logger.error { "Failed to parse B exchange rates:'$it'" }
            emptyList()
        }
        try {
            CompletableFuture.allOf(tableAJob, tableBJob, tableCJob).join()
            logger.debug { "ExchangeRates successfully loaded" }
        } catch (e: Exception) {
            logger.error { "Failed to download exchange rates for date: ${LocalDate.now()}" }
        }
    }

    fun getCurrencyExchangeRate(currency: CurrencyCode): ExchangeRate {
        return repository.findByCodeOrderByEffectiveDateDesc(currency).firstOrNull()
                ?: throw ExchangeRateNotFound(currency)
    }

    private companion object : KLogging()
}