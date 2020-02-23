package com.analyzer.my.dto.secondarymarket

import com.analyzer.my.dto.QuasiBoolean
import com.analyzer.my.dto.PaymentTerm
import java.math.BigDecimal
import java.math.MathContext
import java.time.Duration
import java.time.LocalDate
import java.time.temporal.ChronoUnit

data class SecondaryLoan(
        val country: String,
        val loanId: String,
        val issueDate: LocalDate,
        val closingDate: LocalDate,
        val loanType: String,
        val amortizationMethod: String,
        val loanOriginator: String,
        val rating: String,
        val ltv: Int,
        val interestRate: Float,
        val termInMonths: PaymentTerm,
        val paymentsReceived: Int,
        val loanStatus: String,
        val ytm: Float,
        val amountAvailableForInvestment: Float,
        val price: Float,
        val discountOrPremium: Float,
        val buybackGuarantee: QuasiBoolean,
        val scheduleExtension: QuasiBoolean,
        val myInvestment: Float,
        val currency: String,
        val borrowerApr: Float?
) : Loan {

    companion object {
        private val yearDuration = 365.toBigDecimal()
    }

    fun isLate() = termInMonths.isLate()
    fun isNotLate() = termInMonths.isNotLate()
    fun isGuaranteed() = buybackGuarantee == QuasiBoolean.YES
    fun noScheduleExtension() = scheduleExtension == QuasiBoolean.NO

    private fun decimalInterest() = interestRate.toBigDecimal().divide(100.toBigDecimal())

    override fun worstCaseYield(): Float {
        val daysTillClosing = daysTillClosing()
        val interest = countDailyYield().multiply(daysTillClosing)
        val additionalValue = amountAvailableForInvestment.toBigDecimal().minus(price.toBigDecimal())
        return interest
                .plus(additionalValue)
                .div(yearDuration)
                .multiply(daysTillClosing)
                .toFloat()
    }

    private fun daysTillClosing(): BigDecimal {
        val now = LocalDate.now()
        return ChronoUnit.DAYS
                .between(now, closingDate)
                .toBigDecimal()
    }

    fun countDailyYield() = amountAvailableForInvestment.toBigDecimal()
            .multiply(decimalInterest())
            .divide(yearDuration, MathContext.DECIMAL64)!!
}
