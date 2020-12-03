package net.vizja.currencystock.exchangeaccount.commands

import net.vizja.currencystock.exchangeaccount.ExchangeAccount
import net.vizja.currencystock.exchangerate.ExchangeRate
import java.math.BigDecimal

data class ExchangeCurrencyCommand(
        val amount: BigDecimal,
        val exchangeAccount: ExchangeAccount,
        val exchangeRate: ExchangeRate
)