package com.analyzer.my.dto.secondarymarket

import com.analyzer.my.dto.PaymentTerm
import com.analyzer.my.dto.QuasiBoolean
import java.time.LocalDate


internal fun secondaryLoan(interestRate: Float, amountAvailableForInvestment: Float): SecondaryLoan {
    val discountOrPremium = 0.01F
    return SecondaryLoan(
            "some_country",
            "some_id",
            LocalDate.MAX,
            LocalDate.MIN,
            "some_type",
            "some_method",
            "some_originator",
            "some rating",
            3,
            interestRate,
            PaymentTerm("Late"),
            2,
            "status",
            9.0F,
            amountAvailableForInvestment,
            amountAvailableForInvestment - amountAvailableForInvestment * discountOrPremium,
            discountOrPremium,
            QuasiBoolean.YES,
            QuasiBoolean.NO,
            0F,
            "currency",
            0.1F
    )
}