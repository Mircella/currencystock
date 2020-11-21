package net.vizja.currencystock.accounts

import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class AccountService(
        private val accountRepository: AccountRepository,
        private val passwordEncoder: PasswordEncoder
) {

    fun createAccount(createAccountDetails: CreateAccountDetails): UUID {
        val id = UUID.randomUUID()
        val encryptedPassword = passwordEncoder.encode(createAccountDetails.password)
        val account = createAccountDetails.toAccount(id, encryptedPassword)
        return accountRepository.save(account).id
    }

    fun findById(id: UUID): GetAccountDetails {
        val account = accountRepository.findById(id).map { it }.orElseThrow { UsernameNotFoundException("cannot find user: '${id}'") }
        return GetAccountDetails(account.login)

    }
}