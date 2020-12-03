package net.vizja.currencystock.api.requests

import com.neovisionaries.i18n.CurrencyCode
import net.vizja.currencystock.ExchangeCurrencyForAccountCommand
import java.math.BigDecimal
import java.util.*

data class ExchangeCurrencyRequest(
        val accountId: UUID,
        val amount: BigDecimal,
        val currencyCode: CurrencyCode
) {
    fun toCommand() = ExchangeCurrencyForAccountCommand(accountId, amount, currencyCode)
}
