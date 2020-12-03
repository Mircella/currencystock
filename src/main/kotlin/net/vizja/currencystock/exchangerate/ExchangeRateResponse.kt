package net.vizja.currencystock.exchangerate

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.neovisionaries.i18n.CurrencyCode
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

@JsonIgnoreProperties(ignoreUnknown = true)
data class ExchangeRateCResponse(
        val effectiveDate: LocalDate,
        val rates: List<ExchangeRateC>
) {
    fun toDomain(): List<ExchangeRate> {
        return rates.map {
            ExchangeRate(
                    UUID.randomUUID(),
                    CurrencyCode.getByCode(it.code),
                    BigDecimal.valueOf(it.buyPrice),
                    BigDecimal.valueOf(it.sellPrice),
                    effectiveDate
            )
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class ExchangeRateC(
            val code: String,
            @JsonProperty("bid") val buyPrice: Double,
            @JsonProperty("ask") val sellPrice: Double)
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class ExchangeRateABResponse(
        val effectiveDate: LocalDate,
        val rates: List<ExchangeRateB>
) {

    fun toDomain(): List<ExchangeRate> {
        return rates.map {
            ExchangeRate(
                    UUID.randomUUID(),
                    CurrencyCode.getByCode(it.code),
                    BigDecimal.valueOf(it.middlePrice),
                    BigDecimal.valueOf(it.middlePrice),
                    effectiveDate
            )
        }
    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    data class ExchangeRateB(
            val code: String,
            @JsonProperty("mid") val middlePrice: Double
    )
}