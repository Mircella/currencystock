package net.vizja.currencystock.exchangeaccount.accounts

import net.vizja.currencystock.exchangeaccount.ExchangeAccount
import java.util.*
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "accounts")
class Account(
        @Id
        @Column(name = "id")
        val id: UUID,
        @Column(name = "login", nullable = false, unique = true)
        val login: String,
        @OneToOne(
                mappedBy = "account",
                cascade = [CascadeType.ALL],
                fetch = FetchType.LAZY,
                optional = true
        )
        val exchangeAccount: ExchangeAccount? = null
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Account

        return Objects.equals(this.id, other.id)
                && Objects.equals(this.login, other.login)
                && Objects.equals(this.exchangeAccount, other.exchangeAccount)
    }

    override fun hashCode(): Int {
        return Objects.hash(id, login, exchangeAccount)
    }

    override fun toString(): String {
        return "Account(id=$id, login=$login)"
    }
}