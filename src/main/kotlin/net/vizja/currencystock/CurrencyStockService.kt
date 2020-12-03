package net.vizja.currencystock

import com.neovisionaries.i18n.CurrencyCode
import net.vizja.currencystock.exchangeaccount.ExchangeAccount
import net.vizja.currencystock.exchangeaccount.ExchangeAccountService
import net.vizja.currencystock.exchangeaccount.commands.ExchangeCurrencyCommand
import net.vizja.currencystock.exchangerate.ExchangeRate
import net.vizja.currencystock.exchangerate.ExchangeRateService
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.MathContext
import java.util.*
import javax.transaction.Transactional

@Service
class CurrencyStockService(
        private val exchangeRateService: ExchangeRateService,
        private val exchangeAccountService: ExchangeAccountService
) {

    fun getCurrencyExchangeRate(currency: String): ExchangeRate {
        val currencyCode = CurrencyCode.getByCode(currency)
        return exchangeRateService.getCurrencyExchangeRate(currencyCode)
    }

    @Transactional
    fun buyCurrency(command: ExchangeCurrencyForAccountCommand): UUID {
        validateOperationIsValid(command.currencyCode)
        val exchangeAccount = exchangeAccountService.getExchangeAccount(command.accountId)
        val exchangeRate = calculateExchangeRate(command.currencyCode, exchangeAccount.currencyCode)
        val amount = calculateAmount(command.amount, exchangeRate)
        return exchangeAccountService.buyCurrencyForExchangeAccount(ExchangeCurrencyCommand(amount, exchangeAccount, exchangeRate))
    }

    @Transactional
    fun sellCurrency(command: ExchangeCurrencyForAccountCommand): UUID {
        validateOperationIsValid(command.currencyCode)
        val exchangeAccount = exchangeAccountService.getExchangeAccount(command.accountId)
        val exchangeRate = calculateExchangeRate(command.currencyCode, exchangeAccount.currencyCode)
        val amount = calculateAmount(command.amount, exchangeRate)
        return exchangeAccountService.sellCurrencyForExchangeAccount(ExchangeCurrencyCommand(amount, exchangeAccount, exchangeRate))
    }

    private fun calculateExchangeRate(targetCurrencyCode: CurrencyCode, exchangeAccountCurrencyCode: CurrencyCode): ExchangeRate {
        val targetExchangeRate = exchangeRateService.getCurrencyExchangeRate(targetCurrencyCode)
        val exchangeAccountExchangeRate = exchangeRateService.getCurrencyExchangeRate(exchangeAccountCurrencyCode)
        val buyPrice = targetExchangeRate.buyPrice.divide(exchangeAccountExchangeRate.buyPrice, MathContext.DECIMAL32)
        val sellPrice = targetExchangeRate.sellPrice.divide(exchangeAccountExchangeRate.sellPrice, MathContext.DECIMAL32)
        return ExchangeRate(
                id = UUID.randomUUID(),
                code = targetExchangeRate.code,
                buyPrice = buyPrice,
                sellPrice = sellPrice,
                effectiveDate = targetExchangeRate.effectiveDate
        )
    }

    private fun validateOperationIsValid(currencyCode: CurrencyCode) {
        if (currencyCode == CurrencyCode.PLN) {
            throw CurrencyOperationForbidden(CurrencyCode.PLN)
        }
    }

    private fun calculateAmount(targetAmount: BigDecimal, exchangeRate: ExchangeRate): BigDecimal {
        return targetAmount.multiply(exchangeRate.buyPrice)
    }
}