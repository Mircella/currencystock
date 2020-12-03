package net.vizja.currencystock.exchangeaccount.commands

import com.neovisionaries.i18n.CurrencyCode
import java.math.BigDecimal
import java.util.*

data class CreateExchangeAccountCommand(
        val accountId: UUID,
        val amount: BigDecimal?,
        val currencyCode: CurrencyCode
)