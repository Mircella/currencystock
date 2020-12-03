package net.vizja.currencystock

import com.neovisionaries.i18n.CurrencyCode
import java.math.BigDecimal
import java.util.*

data class ExchangeCurrencyForAccountCommand(
        val accountId: UUID,
        val amount: BigDecimal,
        val currencyCode: CurrencyCode
)
