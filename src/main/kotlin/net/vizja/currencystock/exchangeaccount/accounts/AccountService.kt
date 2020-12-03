package net.vizja.currencystock.exchangeaccount.accounts

import org.springframework.stereotype.Service
import java.util.*

@Service
class AccountService(
        private val accountRepository: AccountRepository
) {

    fun createAccount(createAccountDetails: CreateAccountDetails): UUID {
        val id = UUID.randomUUID()
        val account = createAccountDetails.toAccount(id)
        return accountRepository.save(account).id
    }

    fun findById(id: UUID): Account {
        return accountRepository.findById(id).map { it }.orElseThrow { UsernameNotFoundException(id.toString()) }
    }
}