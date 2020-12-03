package net.vizja.currencystock.api

import net.vizja.currencystock.CurrencyStockService
import net.vizja.currencystock.api.requests.ExchangeCurrencyRequest
import net.vizja.currencystock.api.responses.ExchangeAccountUpdatedResponse
import net.vizja.currencystock.api.responses.SuccessResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/currency-stock")
class CurrencyStockController(private val currencyStockService: CurrencyStockService) {

    @PostMapping("/buy")
    fun buyCurrency(@RequestBody request: ExchangeCurrencyRequest): SuccessResponse {
        val accountId = currencyStockService.buyCurrency(request.toCommand())
        return ExchangeAccountUpdatedResponse(accountId)
    }

    @PostMapping("/sell")
    fun sellCurrency(@RequestBody request: ExchangeCurrencyRequest): SuccessResponse {
        val accountId = currencyStockService.sellCurrency(request.toCommand())
        return ExchangeAccountUpdatedResponse(accountId)
    }


}