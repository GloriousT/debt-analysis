package com.analyzer.my.dto

enum class BuybackGuarantee(private val isGuaranteed: Boolean) {

    YES(true), NO(false);

    companion object {
        fun fromValue(value: String) = values().find { it.name.toLowerCase() == value.toLowerCase() }!!
    }
}