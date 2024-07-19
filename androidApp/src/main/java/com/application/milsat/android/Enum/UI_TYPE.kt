package com.application.milsat.android.Enum

enum class UI_TYPE {
    TEXT_FIELD,
    DROP_DOWN;

    fun toPath() = when (this) {
        TEXT_FIELD -> "text_field"
        DROP_DOWN -> "drop_down"
    }
}

