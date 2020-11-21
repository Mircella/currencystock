package net.vizja.currencystock.api

import net.vizja.currencystock.accounts.CreateAccountDetails
import net.vizja.currencystock.accounts.AccountService
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("accounts")
class AccountController(private val accountService: AccountService) {

    @PostMapping
    fun createAccount(@RequestBody details: CreateAccountDetails): AccountCreatedResponse {
        return AccountCreatedResponse(accountService.createAccount(details))
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID): AccountResponse {
        val details = accountService.findById(id)
        return AccountResponse(details.login)
    }
}