package com.application.milsat.android.Enum

enum class COLUMN_TYPE {
    TEXT;

    fun toPath() = when (this) {
        TEXT -> "TEXT"
    }
}
