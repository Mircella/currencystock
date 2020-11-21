package net.vizja.currencystock.accounts

import net.vizja.currencystock.accounts.Account
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface AccountRepository: JpaRepository<Account, UUID> {
    fun findByLogin(login: String): Account
}