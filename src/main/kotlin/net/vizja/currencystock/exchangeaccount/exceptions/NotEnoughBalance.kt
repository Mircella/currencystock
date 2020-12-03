package net.vizja.currencystock.exchangeaccount.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import java.util.*

class NotEnoughBalance(accountId: UUID)
    : ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough balance for account:'$accountId' to perform operation")