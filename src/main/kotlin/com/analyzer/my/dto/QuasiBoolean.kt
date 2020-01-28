package com.analyzer.my.dto

enum class QuasiBoolean(private val value: Boolean) {

    YES(true), NO(false);

    companion object {
        fun fromValue(value: String) = values().find { it.name.toLowerCase() == value.toLowerCase() }!!
    }
}