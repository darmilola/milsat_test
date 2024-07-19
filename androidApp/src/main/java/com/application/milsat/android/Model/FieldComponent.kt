package com.application.milsat.android.Model

import com.application.milsat.android.Enum.COLUMN_TYPE
import com.application.milsat.android.Enum.UI_TYPE
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import java.io.InputStream
import java.util.Scanner

@Serializable
data class FieldComponent(@SerialName("ui_type") val uiType: String = UI_TYPE.TEXT_FIELD.toPath(),
                              @SerialName("column_type") val columnType: String = COLUMN_TYPE.TEXT.toPath(),
                              @SerialName("column_name") val columnName: String = "",
                              @SerialName("required") val isRequired: Boolean = false,
                              @SerialName("min_length") val minLength: Int = 0,
                              @SerialName("max_length") val maxLength: Int = 0,
                              @SerialName("values") val dropDownValues: ArrayList<String> = arrayListOf())