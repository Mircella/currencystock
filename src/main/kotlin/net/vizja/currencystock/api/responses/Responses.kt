package net.vizja.currencystock.api.responses

import com.neovisionaries.i18n.CurrencyCode
import net.vizja.currencystock.exchangeaccount.ExchangeAccountHistory
import net.vizja.currencystock.exchangeaccount.ExchangeTransactionDto
import net.vizja.currencystock.exchangeaccount.TransactionType
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

sealed class SuccessResponse(val message: String)

data class AccountCreatedResponse(val id: UUID) : SuccessResponse("Account:'$id' created")
data class ExchangeAccountCreatedResponse(val accountId: UUID) : SuccessResponse("ExchangeAccount created successfully")
data class ExchangeAccountUpdatedResponse(val accountId: UUID) : SuccessResponse("ExchangeAccount updated successfully")
data class AccountResponse(val login: String)
data class ExchangeAccountResponse(val accountId: UUID, val amount: BigDecimal, val currencyCode: String)
data class ExchangeAccountHistoryResponse(val accountId: UUID, val transactions: List<ExchangeTransactionResponse>) {
    companion object {
        fun from(history: ExchangeAccountHistory) = ExchangeAccountHistoryResponse(
                history.accountId,
                history.transactions.map { ExchangeTransactionResponse.from(it) }
        )
    }
}

data class ExchangeTransactionResponse(
        val happenedAt: LocalDateTime,
        val type: TransactionType,
        val amount: BigDecimal,
        val currencyCode: CurrencyCode,
        val exchangeRate: BigDecimal?
) {
    companion object {
        fun from(transaction: ExchangeTransactionDto) = with(transaction) {
            ExchangeTransactionResponse(happenedAt, type, amount, currencyCode, exchangeRate)
        }
    }
}