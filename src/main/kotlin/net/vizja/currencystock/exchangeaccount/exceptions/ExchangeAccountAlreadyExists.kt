package net.vizja.currencystock.exchangeaccount.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import java.util.*

class ExchangeAccountAlreadyExists(accountId: UUID) : ResponseStatusException(
        HttpStatus.CONFLICT,
        "Account:'${accountId}' already has exchange account"
)
