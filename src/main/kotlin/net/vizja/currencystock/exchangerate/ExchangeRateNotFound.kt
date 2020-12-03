package net.vizja.currencystock.exchangerate

import com.neovisionaries.i18n.CurrencyCode
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class ExchangeRateNotFound(currencyCode: CurrencyCode)
    : ResponseStatusException(HttpStatus.NOT_FOUND, "Exchange rate for:'$currencyCode' not found")
