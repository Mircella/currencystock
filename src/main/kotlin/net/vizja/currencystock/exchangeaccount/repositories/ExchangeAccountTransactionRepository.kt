package net.vizja.currencystock.exchangeaccount.repositories

import net.vizja.currencystock.exchangeaccount.ExchangeAccount
import net.vizja.currencystock.exchangeaccount.ExchangeAccountTransaction
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository

interface ExchangeAccountTransactionRepository : JpaRepository<ExchangeAccountTransaction, Long>{
    fun findByExchangeAccountOrderByHappenedAtDesc(exchangeAccount: ExchangeAccount): List<ExchangeAccountTransaction>
}