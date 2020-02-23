package com.analyzer.my.dto.secondarymarket

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

import java.time.LocalDate

class SecondaryLoanTest {

    private val commonClosingDate = LocalDate.of(2020, 2, 1)
    private val commonLoan = secondaryLoan(interestRate = 10F, amountAvailableForInvestment = 10F)


    @Test
    fun shouldCountDailyYield() {
        val loan = commonLoan

        val dailyYield = loan.countDailyYield()

        assertThat(dailyYield).isEqualTo("0.002739726027397260")
    }

    @Test
    fun shouldCountDailyYield2() {
        val loan = secondaryLoan(365F, 100F)

        val dailyYield = loan.countDailyYield()

        assertThat(dailyYield).isEqualTo("1.000")
    }

    @Test
    fun shouldReturnZeroForEqualLoans() {
        val loan1 = commonLoan.copy(closingDate = commonClosingDate)
        val loan2 = commonLoan.copy(closingDate = commonClosingDate)

        val compare = loan1.compareTo(loan2)

        assertThat(compare).isEqualTo(0)
    }

    @Test
    fun shouldReturnOneForLoanThatHasEarlierClosingDate() {
        val earlierClosingDateLoan = commonLoan.copy(closingDate = commonClosingDate)
        val laterClosingDateLoan = commonLoan.copy(closingDate = commonClosingDate.plusDays(1))

        val compare = earlierClosingDateLoan.compareTo(laterClosingDateLoan)

        assertThat(compare).isEqualTo(1)
    }

    @Test
    fun shouldReturnMinusOneForLoanThatHasLaterClosingDate() {
        val earlierClosingDateLoan = commonLoan.copy(closingDate = commonClosingDate)
        val laterClosingDateLoan = commonLoan.copy(closingDate = commonClosingDate.plusDays(1))

        val compare = laterClosingDateLoan.compareTo(earlierClosingDateLoan)

        assertThat(compare).isEqualTo(-1)
    }
}