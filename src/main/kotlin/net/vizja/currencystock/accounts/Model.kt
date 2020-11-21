package net.vizja.currencystock.accounts

import java.util.*

data class CreateAccountDetails(val login: String, val password: String) {
    fun toAccount(id: UUID, encryptedPassword: String) = Account(id, login, encryptedPassword)
}

data class GetAccountDetails(val login: String)