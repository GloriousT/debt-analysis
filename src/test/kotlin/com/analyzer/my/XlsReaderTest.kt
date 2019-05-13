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
        assertThat(notLateLoans.size()).isLessThan(loans.size())
        notLateLoans.get().forEach { assertThat(it.isLate()).isFalse() }
    }
}