package net.vizja.currencystock.security

import net.vizja.currencystock.accounts.Account
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CurrencyStockUserPrincipal(private val account: Account) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority?>? {
        return setOf(SimpleGrantedAuthority("USER"))
    }

    override fun getPassword(): String? {
        return account.password
    }

    override fun getUsername(): String? {
        return account.login
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}
