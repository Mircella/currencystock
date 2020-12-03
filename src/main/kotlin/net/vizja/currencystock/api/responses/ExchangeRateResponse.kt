package net.vizja.currencystock.api.responses

import net.vizja.currencystock.exchangerate.ExchangeRate
import java.math.BigDecimal
import java.time.LocalDate

data class ExchangeRateResponse(
        val code: String,
        val buyPrice: BigDecimal,
        val sellPrice: BigDecimal,
        val date: LocalDate
) {
    companion object {
        fun from(exchangeRate: ExchangeRate) = ExchangeRateResponse(
                exchangeRate.code.name,
                exchangeRate.buyPrice,
                exchangeRate.sellPrice,
                exchangeRate.effectiveDate
        )
    }
}
