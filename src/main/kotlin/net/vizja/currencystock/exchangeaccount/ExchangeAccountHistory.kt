package net.vizja.currencystock.exchangeaccount

import com.neovisionaries.i18n.CurrencyCode
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class ExchangeAccountHistory(val accountId: UUID, val transactions: List<ExchangeTransactionDto>)

data class ExchangeTransactionDto(
        val happenedAt: LocalDateTime,
        val type: TransactionType,
        val amount: BigDecimal,
        val currencyCode: CurrencyCode,
        val exchangeRate: BigDecimal?
)

