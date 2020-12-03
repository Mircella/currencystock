package net.vizja.currencystock.exchangeaccount

import com.neovisionaries.i18n.CurrencyCode
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

class ExchangeTransactionDetails(val exchangeRate: BigDecimal, val currencyCode: CurrencyCode, val date: LocalDate) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ExchangeTransactionDetails
        return Objects.equals(this.exchangeRate, other.exchangeRate)
                && Objects.equals(this.currencyCode, other.currencyCode)
                && Objects.equals(this.date, other.date)
    }

    override fun hashCode(): Int {
        return Objects.hash(exchangeRate, date, currencyCode)
    }

    override fun toString(): String {
        return "ExchangeTransactionDetails(exchangeRate=$exchangeRate, date=$date, currencyCode=$currencyCode)"
    }
}
