package com.analyzer.my.datetime

import java.time.format.DateTimeFormatterBuilder
import java.time.format.SignStyle
import java.time.temporal.ChronoField

val secondaryMarketDateFormat = DateTimeFormatterBuilder()
    .appendValue(ChronoField.DAY_OF_MONTH, 2)
    .appendLiteral('.')
    .appendValue(ChronoField.MONTH_OF_YEAR, 2)
    .appendLiteral('.')
    .appendValue(ChronoField.YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
    .toFormatter()!!
