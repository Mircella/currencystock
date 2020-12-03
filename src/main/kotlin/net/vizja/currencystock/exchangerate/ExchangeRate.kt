package net.vizja.currencystock.exchangerate

import com.neovisionaries.i18n.CurrencyCode
import net.vizja.currencystock.exchangeaccount.TransactionType
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "exchange_rates")
class ExchangeRate(
        @Id
        @Column(name = "id")
        val id: UUID,
        @Enumerated(EnumType.STRING)
        @Column(name = "code")
        val code: CurrencyCode,
        @Column(name = "buy_price", nullable = false)
        val buyPrice: BigDecimal,
        @Column(name = "sell_price", nullable = false)
        val sellPrice: BigDecimal,
        @Column(name = "effective_date", nullable = false)
        val effectiveDate: LocalDate
)