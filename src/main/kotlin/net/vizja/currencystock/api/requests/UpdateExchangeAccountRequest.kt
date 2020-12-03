package net.vizja.currencystock.api.requests

import net.vizja.currencystock.exchangeaccount.commands.UpdateExchangeAccountCommand
import java.math.BigDecimal
import java.util.*

data class UpdateExchangeAccountRequest (
        val accountId: UUID,
        val amount: BigDecimal
){
    fun toCommand()= UpdateExchangeAccountCommand(accountId, amount)
}
