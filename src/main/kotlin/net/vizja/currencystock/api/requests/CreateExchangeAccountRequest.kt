package net.vizja.currencystock.api.requests

import com.neovisionaries.i18n.CurrencyCode
import net.vizja.currencystock.exchangeaccount.commands.CreateExchangeAccountCommand
import java.math.BigDecimal
import java.util.*

data class CreateExchangeAccountRequest(
        val accountId: UUID,
        val amount: BigDecimal,
        val currencyCode: CurrencyCode
){
    fun toCommand() = CreateExchangeAccountCommand(accountId, amount, currencyCode)
}