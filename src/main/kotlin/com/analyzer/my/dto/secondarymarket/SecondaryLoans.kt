package com.analyzer.my.dto.secondarymarket

data class SecondaryLoans(private val loans: List<SecondaryLoan>) {

    fun isEmpty() = loans.isEmpty()

    fun size() = loans.size

    fun notLate() = SecondaryLoans(loans.filter { it.isNotLate() })

    fun get() = loans
}