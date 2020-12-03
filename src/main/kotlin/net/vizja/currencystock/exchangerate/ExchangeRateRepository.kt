package net.vizja.currencystock.exchangerate

import com.neovisionaries.i18n.CurrencyCode
import org.hibernate.boot.model.source.spi.Sortable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.*

@Repository
interface ExchangeRateRepository : JpaRepository<ExchangeRate, UUID> {

    fun findByCodeAndEffectiveDate(code: CurrencyCode, effectiveDate: LocalDate): ExchangeRate?

    fun findByCodeOrderByEffectiveDateDesc(code: CurrencyCode): List<ExchangeRate>

    @Query("select new ExchangeRate(er.id, er.code, er.buyPrice, er.sellPrice, max(er.effectiveDate)) from ExchangeRate er where er.code = :code")
    fun findByCodeAndMaxEffectiveDate(@Param("code") code: CurrencyCode): ExchangeRate?
}