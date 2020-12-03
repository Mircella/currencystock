package net.vizja.currencystock.exchangeaccount.accounts

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface AccountRepository: JpaRepository<Account, UUID> {
    fun findByLogin(login: String): Account
}