package net.vizja.currencystock.exchangeaccount.repositories

import net.vizja.currencystock.exchangeaccount.ExchangeAccount
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import java.util.*

@Repository
interface ExchangeAccountRepository : JpaRepository<ExchangeAccount, UUID> {

    fun existsByAccountId(accountId: UUID): Boolean
    fun findByAccountId(accountId: UUID): ExchangeAccount?

    @Modifying
    @Query("update ExchangeAccount ea set ea.amount = ea.amount + :amount where ea.account.id = :accountId")
    fun topUp(@Param("accountId") accountId: UUID, @Param("amount") amount: BigDecimal)

    @Modifying
    @Query("update ExchangeAccount ea set ea.amount = ea.amount - :amount where ea.account.id = :accountId")
    fun withdraw(@Param("accountId") accountId: UUID, @Param("amount") amount: BigDecimal)
}