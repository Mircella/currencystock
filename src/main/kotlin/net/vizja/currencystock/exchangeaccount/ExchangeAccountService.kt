package net.vizja.currencystock.exchangeaccount

import com.neovisionaries.i18n.CurrencyCode
import mu.KLogging
import net.vizja.currencystock.exchangeaccount.accounts.Account
import net.vizja.currencystock.exchangeaccount.accounts.AccountService
import net.vizja.currencystock.exchangeaccount.commands.ExchangeCurrencyCommand
import net.vizja.currencystock.exchangeaccount.commands.CreateExchangeAccountCommand
import net.vizja.currencystock.exchangeaccount.commands.UpdateExchangeAccountCommand
import net.vizja.currencystock.exchangeaccount.exceptions.ExchangeAccountAlreadyExists
import net.vizja.currencystock.exchangeaccount.exceptions.ExchangeAccountDoesNotExist
import net.vizja.currencystock.exchangeaccount.exceptions.InvalidCurrencyCode
import net.vizja.currencystock.exchangeaccount.exceptions.NotEnoughBalance
import net.vizja.currencystock.exchangeaccount.repositories.ExchangeAccountRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.*
import javax.transaction.Transactional

@Service
class ExchangeAccountService(
        private val exchangeAccountRepository: ExchangeAccountRepository,
        private val exchangeAccountTransactionService: ExchangeAccountTransactionService,
        private val accountService: AccountService
) {

    @Transactional
    fun createExchangeAccount(command: CreateExchangeAccountCommand): UUID {
        val account = accountService.findById(command.accountId)
        validateAccountHasNoExchangeAccount(account)
        validateCurrencyCode(command)
        val exchangeAccount = ExchangeAccount(
                id = UUID.randomUUID(),
                amount = command.amount ?: BigDecimal.ZERO,
                currencyCode = command.currencyCode,
                account = account,
                transactions = emptyList()
        )
        val savedExchangeAccount = exchangeAccountRepository.save(exchangeAccount).also {
            logger.debug { "ExchangeAccount:'$it' created" }
        }
        command.amount?.let {
            exchangeAccountTransactionService.addTopUpTransaction(savedExchangeAccount, it)
        }
        return savedExchangeAccount.account.id
    }

    fun getExchangeAccount(accountId: UUID): ExchangeAccount {
        return exchangeAccountRepository.findByAccountId(accountId)
                ?: throw ExchangeAccountDoesNotExist(accountId)
    }

    fun getExchangeAccountHistory(accountId: UUID): ExchangeAccountHistory {
        val exchangeAccount = getExchangeAccount(accountId)
        val transactions = exchangeAccountTransactionService.findAllForExchangeAccount(exchangeAccount)
                .map { it.toDomain() }
        return ExchangeAccountHistory(accountId, transactions)
    }

    @Transactional
    fun topUpExchangeAccount(command: UpdateExchangeAccountCommand): UUID {
        val exchangeAccount = getExchangeAccount(command.accountId)
        exchangeAccountRepository.topUp(command.accountId, command.amount).also {
            logger.debug { "ExchangeAccount:'$it' topped up by ${command.amount}" }
        }
        exchangeAccountTransactionService.addTopUpTransaction(exchangeAccount, command.amount)
        return exchangeAccount.account.id
    }

    @Transactional
    fun withdrawExchangeAccount(command: UpdateExchangeAccountCommand): UUID {
        val exchangeAccount = getExchangeAccount(command.accountId)
        if (exchangeAccount.amount.subtract(command.amount).compareTo(BigDecimal.ZERO) < 0) {
            throw NotEnoughBalance(command.accountId)
        }
        exchangeAccountRepository.withdraw(command.accountId, command.amount).also {
            logger.debug { "ExchangeAccount:'$it' withdrawn by ${command.amount}" }
        }
        exchangeAccountTransactionService.addWithdrawTransaction(exchangeAccount, command.amount)
        return exchangeAccount.account.id
    }

    @Transactional
    fun buyCurrencyForExchangeAccount(command: ExchangeCurrencyCommand): UUID {
        if (command.exchangeAccount.amount.subtract(command.amount).compareTo(BigDecimal.ZERO) < 0) {
            throw NotEnoughBalance(command.exchangeAccount.account.id)
        }
        exchangeAccountRepository.withdraw(command.exchangeAccount.account.id, command.amount).also {
            logger.debug { "ExchangeAccount:'$it' withdrawn by ${command.amount}" }
        }
        exchangeAccountTransactionService.addBuyTransaction(command.exchangeAccount, command.amount, command.exchangeRate)
        return command.exchangeAccount.account.id
    }

    @Transactional
    fun sellCurrencyForExchangeAccount(command: ExchangeCurrencyCommand): UUID {
        exchangeAccountRepository.topUp(command.exchangeAccount.account.id, command.amount).also {
            logger.debug { "ExchangeAccount:'$it' withdrawn by ${command.amount}" }
        }
        exchangeAccountTransactionService.addSellTransaction(command.exchangeAccount, command.amount, command.exchangeRate)
        return command.exchangeAccount.account.id
    }

    private fun validateCurrencyCode(command: CreateExchangeAccountCommand) {
        if (command.currencyCode == CurrencyCode.UNDEFINED) {
            throw InvalidCurrencyCode(command.currencyCode)
        }
    }

    private fun validateAccountHasNoExchangeAccount(account: Account) {
        if (account.exchangeAccount != null) {
            throw ExchangeAccountAlreadyExists(account.id)
        }
    }

    private companion object : KLogging()
}