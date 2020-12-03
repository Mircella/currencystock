package net.vizja.currencystock.exchangeaccount.exceptions

import com.neovisionaries.i18n.CurrencyCode
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class InvalidCurrencyCode(currencyCode: CurrencyCode) : ResponseStatusException(
        HttpStatus.BAD_REQUEST,
        "Invalid currency code:'${currencyCode.name}'"
)