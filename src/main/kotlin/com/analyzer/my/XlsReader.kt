package com.analyzer.my

import com.analyzer.my.dto.PaymentTerm
import com.analyzer.my.datetime.secondaryMarketDateFormat
import com.analyzer.my.dto.QuasiBoolean
import com.analyzer.my.dto.secondarymarket.SecondaryLoan
import com.analyzer.my.dto.secondarymarket.SecondaryLoans
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.FileInputStream
import java.time.LocalDate

class XlsReader private constructor(private val excelFile: FileInputStream) {

    companion object {
        fun fromResources(excelFileLocation: String): XlsReader {
            val file = ClassLoader.getSystemResource(excelFileLocation).file
            return XlsReader(FileInputStream(file))
        }
    }

    fun readSecondaryLoans(): SecondaryLoans {
        val workbook = XSSFWorkbook(excelFile)
        val sheet = workbook.getSheet("Sheet1")
        rejectIfWrongNumberOfColumns(sheet)
        val rows = sheet.iterator()
        //skip header
        rows.next()
        val loans = mutableSetOf<SecondaryLoan>()
        rows.forEachRemaining {
            val loan = mapToSecondaryLoan(it.cellIterator())
            loans.add(loan)
        }
        return SecondaryLoans(loans.toList())
    }

    private fun mapToSecondaryLoan(cells: MutableIterator<Cell>): SecondaryLoan {
        val row = cells.asSequence().map { it.toString() }.toList()
        return SecondaryLoan(
            country = row[0],
            loanId = row[1],
            issueDate = LocalDate.parse(row[2], secondaryMarketDateFormat),
            closingDate = LocalDate.parse(row[3], secondaryMarketDateFormat),
            loanType = row[4],
            amortizationMethod = row[5],
            loanOriginator = row[6],
            rating = row[7],
            ltv = row[8].toInt(),
            interestRate = row[9].toFloat(),
            termInMonths = PaymentTerm(row[10]),
            paymentsReceived = row[11].toFloat().toInt(),
            loanStatus = row[12],
            ytm = row[13].toFloat(),
            amountAvailableForInvestment = row[14].toFloat(),
            price = row[15].toFloat(),
            discountOrPremium = row[16].toFloat(),
            buybackGuarantee = QuasiBoolean.fromValue(row[17]),
            scheduleExtension = QuasiBoolean.fromValue(row[18]),
            myInvestment = row[19].toFloat(),
            currency = row[20],
            borrowerApr = readBorrowerApr(row)
        )
    }

    private fun readBorrowerApr(row: List<String>): Float? {
        return if (row.size == 22) {
            row[21].toFloat()
        } else {
            null
        }
    }

    private fun rejectIfWrongNumberOfColumns(sheet: XSSFSheet) {
        val expectedNumberOfColumns = 22
        val firstRow = sheet.first()
        val actualNumberOfColumns = firstRow.toList().size
        if (actualNumberOfColumns != expectedNumberOfColumns) {
            throw RuntimeException(
                """Invalid number of rows for secondary market list!
                Expected $expectedNumberOfColumns but was $actualNumberOfColumns
                The columns are ${firstRow.map { it }}
            """.trimMargin()
            )
        }
    }
}

fun read(file: String) = XlsReader.fromResources(file).readSecondaryLoans()
