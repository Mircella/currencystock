package net.vizja.currencystock.exchangeaccount

import com.neovisionaries.i18n.CurrencyCode
import net.vizja.currencystock.exchangeaccount.accounts.Account
import java.math.BigDecimal
import java.util.*
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "exchange_accounts")
class ExchangeAccount(
        @Id
        @Column(name = "id")
        val id: UUID,
        @Column(name = "amount", nullable = false)
        val amount: BigDecimal,
        @Enumerated(EnumType.STRING)
        @Column(name = "currency_code")
        val currencyCode: CurrencyCode,
        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "account_id", nullable = false)
        val account: Account,
        @OneToMany(
                mappedBy = "exchangeAccount",
                cascade = [CascadeType.ALL],
                orphanRemoval = true,
                fetch = FetchType.LAZY
        )
        val transactions: List<ExchangeAccountTransaction>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ExchangeAccount

        return Objects.equals(this.id, other.id)
                && Objects.equals(this.amount, other.amount)
                && Objects.equals(this.currencyCode, other.currencyCode)
                && Objects.equals(this.account.id, other.account.id)
                && Objects.equals(this.transactions, other.transactions)
    }

    override fun hashCode(): Int {
        return Objects.hash(id, amount, currencyCode, account.id, transactions)
    }

    override fun toString(): String {
        return "ExchangeAccount(id=$id, amount=$amount, currencyCode=$currencyCode, account=${account.id}, transactions=$transactions)"
    }

}