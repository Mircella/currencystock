package net.vizja.currencystock.api

import net.vizja.currencystock.api.requests.CreateExchangeAccountRequest
import net.vizja.currencystock.api.requests.UpdateExchangeAccountRequest
import net.vizja.currencystock.api.responses.ExchangeAccountCreatedResponse
import net.vizja.currencystock.api.responses.ExchangeAccountHistoryResponse
import net.vizja.currencystock.api.responses.ExchangeAccountResponse
import net.vizja.currencystock.api.responses.ExchangeAccountUpdatedResponse
import net.vizja.currencystock.api.responses.SuccessResponse
import net.vizja.currencystock.exchangeaccount.ExchangeAccountService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("exchangeAccounts")
class ExchangeAccountController(private val exchangeAccountService: ExchangeAccountService) {

    @PostMapping
    fun createNewExchangeAccount(@RequestBody request: CreateExchangeAccountRequest): SuccessResponse {
        val accountId = exchangeAccountService.createExchangeAccount(request.toCommand())
        return ExchangeAccountCreatedResponse(accountId)
    }

    @PutMapping("/topUp")
    fun topUpExchangeAccount(@RequestBody request: UpdateExchangeAccountRequest): SuccessResponse {
        val accountId = exchangeAccountService.topUpExchangeAccount(request.toCommand())
        return ExchangeAccountUpdatedResponse(accountId)
    }

    @PutMapping("/withdraw")
    fun withdrawExchangeAccount(@RequestBody request: UpdateExchangeAccountRequest): SuccessResponse {
        val accountId = exchangeAccountService.withdrawExchangeAccount(request.toCommand())
        return ExchangeAccountUpdatedResponse(accountId)
    }

    @GetMapping("/{accountId}")
    fun getExchangeAccount(@PathVariable accountId: UUID): ExchangeAccountResponse {
        val exchangeAccount = exchangeAccountService.getExchangeAccount(accountId)
        return ExchangeAccountResponse(
                exchangeAccount.account.id,
                exchangeAccount.amount,
                exchangeAccount.currencyCode.name
        )
    }

    @GetMapping("/{accountId}/history")
    fun exchangeAccountHistory(@PathVariable accountId: UUID): ExchangeAccountHistoryResponse {
        return ExchangeAccountHistoryResponse.from(exchangeAccountService.getExchangeAccountHistory(accountId))
    }
}