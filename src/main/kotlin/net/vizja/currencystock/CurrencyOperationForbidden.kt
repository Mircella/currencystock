package net.vizja.currencystock

import com.neovisionaries.i18n.CurrencyCode
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class CurrencyOperationForbidden(currencyCode: CurrencyCode)
    : ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot buy/sell currency: $currencyCode")
