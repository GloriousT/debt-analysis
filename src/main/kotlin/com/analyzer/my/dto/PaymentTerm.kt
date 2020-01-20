package com.analyzer.my.dto

data class PaymentTerm(private val termInMonths: String) {

    fun isLate(): Boolean = "Late" == termInMonths

    fun isNotLate() = !isLate()
}