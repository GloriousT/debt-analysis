package com.analyzer.my.dto.secondarymarket


data class SecondaryLoans(private val loans: List<SecondaryLoan>) {

    internal fun loans() = loans.toList()

    //ids are not unique. If multiple investors own parts in the same loan
    //the id's may be present multiple times on a secondary market
    fun ids() = loans.map { it.loanId }

    fun size() = loans.size

    fun isEmpty() = loans.isEmpty()

    fun notLate() = loans.filter { it.isNotLate() }

    fun discount(discountValueLowerOrEqualPositive: Float = 0.1F) =
            loans.filter { it.discountOrPremium <= -discountValueLowerOrEqualPositive }

    fun guaranteed() = loans.filter { it.isGuaranteed() }

    fun currency(currency: String) = loans.filter { it.currency.toLowerCase() == currency.toLowerCase() }
}

private fun Iterable<SecondaryLoan>.filter(predicate: (SecondaryLoan) -> Boolean): SecondaryLoans =
        SecondaryLoans(filterTo(ArrayList(), predicate))
