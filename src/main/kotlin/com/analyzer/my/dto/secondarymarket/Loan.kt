package com.analyzer.my.dto.secondarymarket


interface Loan : Comparable<Loan> {

    fun worstCaseYield(): Float

    override operator fun compareTo(other: Loan): Int =
            this.worstCaseYield().compareTo(other.worstCaseYield())
}