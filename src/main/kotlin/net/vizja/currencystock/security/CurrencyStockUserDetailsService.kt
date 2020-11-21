package net.vizja.currencystock.security

import net.vizja.currencystock.accounts.AccountRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service(value = "currencyStockUserDetailsService")
class CurrencyStockUserDetailsService(private val repository: AccountRepository) : UserDetailsService {

    override fun loadUserByUsername(login: String?): UserDetails {
        val account = login?.let { repository.findByLogin(it) }
        return account?.let { CurrencyStockUserPrincipal(it) }
                ?: throw UsernameNotFoundException("cannot find user: '${login}'")
    }
}