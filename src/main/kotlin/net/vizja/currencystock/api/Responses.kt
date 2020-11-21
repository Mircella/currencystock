package net.vizja.currencystock.api

import java.util.*

sealed class SuccessResponse(val message: String)

data class AccountCreatedResponse(val id: UUID): SuccessResponse("Account:'$id' created")

data class AccountResponse(val login: String)