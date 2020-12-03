package net.vizja.currencystock.exchangeaccount.commands

import java.math.BigDecimal
import java.util.*

data class UpdateExchangeAccountCommand(
        val accountId: UUID, val amount: BigDecimal
)
