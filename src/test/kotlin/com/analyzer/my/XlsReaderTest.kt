package com.analyzer.my

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class XlsReaderTest {

    @Test
    fun shouldReadSecondaryMarketExcel() {
        // when
        val loans = XlsReader.fromResources(SECONDARY_MARKET_FILE).readSecondaryLoans()

        // then
        assertThat(loans.isEmpty()).isFalse()
    }

    @Test(expected = RuntimeException::class)
    fun shouldRejectSecondaryMarketExcelWithWrongNumberOfColumns() {
        // given

        // when
        XlsReader.fromResources(SECONDARY_MARKET_FILE_WRONG_HEADER).readSecondaryLoans()

        // then
    }

    @Test
    fun shouldFilterLateLoans() {
        // given
        val loans = XlsReader.fromResources(SECONDARY_MARKET_FILE).readSecondaryLoans()

        // when
        val notLateLoans = loans.notLate()

        //then
        assertThat(notLateLoans.size()).isEqualTo(121)
        assertThat(notLateLoans.ids()).doesNotContain(
                "10918700-01",
                "10969504-01",
                "11420448-01",
                "9489879-02"
        )
    }

    @Test
    fun shouldFilterDiscountLoans() {
        // given
        val loans = XlsReader.fromResources(SECONDARY_MARKET_FILE).readSecondaryLoans()

        // when
        val discountLoans = loans.discount()

        //then
        assertThat(discountLoans.size()).isEqualTo(5)
        assertThat(discountLoans.ids()).containsExactlyInAnyOrder(
                "4297361-01",
                "5321597-01",
                "5321626-01",
                "5321644-01",
                "5845920-01"
        )
    }

    @Test
    fun shouldFilterGuaranteedLoans() {
        // given
        val loans = XlsReader.fromResources(SECONDARY_MARKET_FILE).readSecondaryLoans()

        // when
        val discountLoans = loans.guaranteed()

        //then
        assertThat(discountLoans.size()).isEqualTo(loans.size() - 1)
        assertThat(discountLoans.ids()).doesNotContain("5321597-01")
    }

    @Test
    fun shouldFilterSpecificCurrency() {
        // given
        val loans = XlsReader.fromResources(SECONDARY_MARKET_FILE).readSecondaryLoans()

        // when
        val discountLoans = loans.currency("PLN")

        //then
        assertThat(discountLoans.size()).isEqualTo(loans.size() - 1)
        assertThat(discountLoans.ids()).doesNotContain("88321644-01")
    }


    @Test
    fun shouldApplyMultipleFilters() {
        val loans = XlsReader.fromResources(SECONDARY_MARKET_FILE).readSecondaryLoans()
        val notLate = loans.notLate()

        val notLateWithDiscount = loans.notLate().discount()

        assertThat(notLate.loans()).containsAnyElementsOf(notLateWithDiscount.loans())
        assertThat(notLate.size()).isGreaterThan(notLateWithDiscount.size())
    }

    @Test
    fun shouldFilterNotLateLoans() {
        val loans = XlsReader.fromResources(SECONDARY_MARKET_FILE).readSecondaryLoans()

        val chainedFilterResult = loans.notLate()
                .discount()
                .guaranteed()
                .currency("PLN")

        assertThat(chainedFilterResult.size()).isEqualTo(3)
        assertThat(chainedFilterResult.ids()).containsExactlyInAnyOrder(
                "5845920-01",
                "4297361-01",
                "5321626-01")
    }
}