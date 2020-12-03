package net.vizja.currencystock.exchangeaccount.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import java.util.*

class ExchangeAccountDoesNotExist(accountId: UUID) : ResponseStatusException(
        HttpStatus.NOT_FOUND,
        "ExchangeAccount for:'$accountId' does not exist"
)

