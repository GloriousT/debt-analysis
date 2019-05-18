package com.analyzer.my.dto

enum class LoanStatus {
    CURRENT,
    GRACE_PERIOD,
    LATE_15_MAX,
    LATE_30_MAX,
    LATE_60_MAX,
    LATE_60_PLUS,
    DEFAULT;
}