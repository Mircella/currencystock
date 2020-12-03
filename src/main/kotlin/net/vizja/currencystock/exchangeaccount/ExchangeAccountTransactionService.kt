package net.vizja.currencystock.exchangeaccount

import com.neovisionaries.i18n.CurrencyCode
import mu.KLogging
import net.vizja.currencystock.exchangeaccount.TransactionType.*
import net.vizja.currencystock.exchangeaccount.repositories.ExchangeAccountTransactionRepository
import net.vizja.currencystock.exchangerate.ExchangeRate
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.transaction.Transactional

@Service
@Transactional
class ExchangeAccountTransactionService(
        private val exchangeAccountTransactionRepository: ExchangeAccountTransactionRepository
) {

    fun addTopUpTransaction(exchangeAccount: ExchangeAccount, amount: BigDecimal) {
        val transaction = createExchangeTransaction(
                TOP_UP, amount, exchangeAccount.currencyCode, exchangeAccount
        )
        exchangeAccountTransactionRepository.save(transaction).also {
            logger.debug { "TopUpExchangeAccountTransaction:'$it' saved" }
        }
    }

    fun addWithdrawTransaction(exchangeAccount: ExchangeAccount, amount: BigDecimal) {
        val transaction = createExchangeTransaction(
                WITHDRAW, amount, exchangeAccount.currencyCode, exchangeAccount
        )
        exchangeAccountTransactionRepository.save(transaction).also {
            logger.debug { "WithdrawExchangeAccountTransaction:'$it' saved" }
        }
    }

    fun addBuyTransaction(exchangeAccount: ExchangeAccount, amount: BigDecimal, exchangeRate: ExchangeRate) {
        val transaction = createExchangeTransaction(
                BUY, amount, exchangeRate.code, exchangeAccount, transactionDetails(exchangeRate, BUY)
        )
        exchangeAccountTransactionRepository.save(transaction).also {
            logger.debug { "BuyExchangeAccountTransaction:'$it' saved" }
        }
    }

    fun addSellTransaction(exchangeAccount: ExchangeAccount, amount: BigDecimal, exchangeRate: ExchangeRate) {
        val transaction = createExchangeTransaction(
                SELL, amount, exchangeRate.code, exchangeAccount, transactionDetails(exchangeRate, SELL)
        )
        exchangeAccountTransactionRepository.save(transaction).also {
            logger.debug { "SellExchangeAccountTransaction:'$it' saved" }
        }
    }

    fun transactionDetails(exchangeRate: ExchangeRate, type: TransactionType): List<ExchangeTransactionDetails> {
        return when (type) {
            BUY -> listOf(ExchangeTransactionDetails(exchangeRate.buyPrice, exchangeRate.code, exchangeRate.effectiveDate))
            SELL -> listOf(ExchangeTransactionDetails(exchangeRate.sellPrice, exchangeRate.code, exchangeRate.effectiveDate))
            else -> emptyList()
        }
    }

    private fun createExchangeTransaction(
            type: TransactionType,
            amount: BigDecimal,
            currencyCode: CurrencyCode,
            exchangeAccount: ExchangeAccount,
            details: List<ExchangeTransactionDetails> = emptyList()
    ): ExchangeAccountTransaction {
        return ExchangeAccountTransaction(
                happenedAt = LocalDateTime.now(),
                type = type,
                targetAmount = amount,
                targetCurrencyCode = currencyCode,
                exchangeAccount = exchangeAccount,
                details = details
        )
    }

    fun findAllForExchangeAccount(exchangeAccount: ExchangeAccount): List<ExchangeAccountTransaction> {
        return exchangeAccountTransactionRepository.findByExchangeAccountOrderByHappenedAtDesc(exchangeAccount)
    }

    private companion object : KLogging()
}
