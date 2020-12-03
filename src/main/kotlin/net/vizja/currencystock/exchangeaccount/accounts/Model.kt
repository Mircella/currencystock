package net.vizja.currencystock.exchangeaccount.accounts

import java.util.*

data class CreateAccountDetails(val login: String) {
    fun toAccount(id: UUID) = Account(id, login)
}
