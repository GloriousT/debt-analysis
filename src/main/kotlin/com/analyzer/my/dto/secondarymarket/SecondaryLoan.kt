package com.analyzer.my.dto.secondarymarket

import com.analyzer.my.dto.BuybackGuarantee
import com.analyzer.my.dto.PaymentTerm
import java.time.LocalDate

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
    val buybackGuarantee: BuybackGuarantee,
    val scheduleExtension: String,
    val myInvestment: Float,
    val currency: String,
    val borrowerApr: Float?
) {

    fun isLate() = termInMonths.isLate()
    fun isNotLate() = termInMonths.isNotLate()
    fun isGuaranteed() = buybackGuarantee == BuybackGuarantee.YES
}
