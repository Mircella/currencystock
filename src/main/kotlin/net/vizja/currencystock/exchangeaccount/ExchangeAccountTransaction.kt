package net.vizja.currencystock.exchangeaccount

import com.neovisionaries.i18n.CurrencyCode
import com.vladmihalcea.hibernate.type.json.JsonBinaryType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*
import javax.persistence.Basic
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "exchange_account_transactions")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType::class)
class ExchangeAccountTransaction(
        @Column(name = "happened_at", nullable = false)
        val happenedAt: LocalDateTime,
        @Enumerated(EnumType.STRING)
        @Column(name = "type", nullable = false)
        val type: TransactionType,
        @Column(name = "target_amount", nullable = false)
        val targetAmount: BigDecimal,
        @Enumerated(EnumType.STRING)
        @Column(name = "target_currency_code")
        val targetCurrencyCode: CurrencyCode,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "exchange_account_id", nullable = false)
        val exchangeAccount: ExchangeAccount,
        @Type(type = "jsonb")
        @Column(columnDefinition = "jsonb")
        @Basic(fetch = FetchType.LAZY)
        val details: List<ExchangeTransactionDetails>
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id",
            unique = true,
            updatable = false,
            nullable = false
    )
    val id : Int = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ExchangeAccountTransaction

        return Objects.equals(this.id, other.id)
                && Objects.equals(this.happenedAt, other.happenedAt)
                && Objects.equals(this.type, other.type)
                && Objects.equals(this.targetAmount, other.targetAmount)
                && Objects.equals(this.targetCurrencyCode, other.targetCurrencyCode)
                && Objects.equals(this.exchangeAccount.id, other.exchangeAccount.id)
    }

    override fun hashCode(): Int {
        return Objects.hash(
                id, happenedAt, type, targetAmount, targetCurrencyCode, exchangeAccount.id
        )
    }

    override fun toString(): String {
        return "ExchangeTransaction(id=$id, happenedAt=$happenedAt, type=$type, targetAmount=$targetAmount, targetCurrencyCode=$targetCurrencyCode, exchangeAccount=${exchangeAccount.id})"
    }

    fun toDomain(): ExchangeTransactionDto {
        return ExchangeTransactionDto(happenedAt, type, targetAmount, targetCurrencyCode, details.firstOrNull()?.exchangeRate)
    }
}