package net.vizja.currencystock.accounts

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "accounts")
class Account(
        @Id
        @Column(name = "id")
        val id: UUID,
        @Column(name = "login", nullable = false, unique = true)
        val login: String,
        @Column(name = "password", nullable = false)
        val password: String
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Account

        return Objects.equals(this.id, other.id)
                && Objects.equals(this.login, other.login)
                && Objects.equals(this.password, other.password);
    }

    override fun hashCode(): Int {
        return Objects.hash(id, login, password)
    }

    override fun toString(): String {
        return "Account(id=$id, login=$login, password=$password)"
    }
}