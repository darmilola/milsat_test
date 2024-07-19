package com.application.milsat.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.application.milsat.Greeting
import com.application.milsat.android.Model.FieldComponent
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val uiList: MutableList<FieldComponent> = mutableListOf<FieldComponent>()

                    val jsonString = getJsonFile()
                    val formFields = getFieldsFromFile(jsonString!!)
                    uiList.add(getNameOfBuilding(formFields))
                    uiList.add(getAddressOfBuilding(formFields))
                    uiList.add(buildingOwnership(formFields))
                    uiList.add(buildingStatus(formFields))
                    uiList.add(ownerFullName(formFields))
                    uiList.add(buildingUses(formFields))
                    uiList.add(ownerFullName(formFields))



                }
            }

        }
    }

    private fun getFieldsFromFile(jsonString: String): JsonObject {
        val rootObject = Json.parseToJsonElement(jsonString) as JsonObject
        val forms = rootObject["forms"] as JsonObject
        val buildingMapping = forms["BUILDING_MAPPING"] as JsonObject
        val pages = buildingMapping["pages"] as JsonObject
        val buildingMapping2 = pages["BUILDING_MAPPING"] as JsonObject

        return buildingMapping2["fields"] as JsonObject
    }


    private fun getNameOfBuilding(jsonObject: JsonObject): FieldComponent {
        return Json.decodeFromJsonElement(
            (FieldComponent.serializer()),
            jsonObject["NAME OF BUILDING"]!!
        )
    }

    private fun getAddressOfBuilding(jsonObject: JsonObject): FieldComponent  {
        return Json.decodeFromJsonElement(
            (FieldComponent.serializer()),
            jsonObject["ADDRESS OF BUILDING"]!!
        )
    }

    private fun buildingOwnership(jsonObject: JsonObject): FieldComponent  {
        return Json.decodeFromJsonElement(
            (FieldComponent.serializer()),
            jsonObject["BUILDING OWNERSHIP"]!!
        )
    }

    private fun buildingStatus(jsonObject: JsonObject): FieldComponent  {
        return Json.decodeFromJsonElement(
            (FieldComponent.serializer()),
            jsonObject["BUILDING STATUS"]!!
        )
    }

    private fun ownerFullName(jsonObject: JsonObject): FieldComponent  {
        return Json.decodeFromJsonElement(
            (FieldComponent.serializer()),
            jsonObject["OWNER'S FULLNAME"]!!
        )
    }

    private fun buildingUses(jsonObject: JsonObject): FieldComponent  {
        return Json.decodeFromJsonElement(
            (FieldComponent.serializer()),
            jsonObject["BUILDING USES"]!!
        )
    }

    private fun ownersPhone(jsonObject: JsonObject): FieldComponent  {
        return Json.decodeFromJsonElement(
            (FieldComponent.serializer()),
            jsonObject["OWNER'S PHONE NUMBER"]!!
        )
    }

    @Throws(IOException::class)
    fun getJsonFile(): String? {
        val jsonFile = assets.open("building_mapping.json")
        val bufferedReader = BufferedReader(InputStreamReader(jsonFile))
        val stringBuilder = StringBuilder()
        bufferedReader.useLines { lines ->
            lines.forEach {
                stringBuilder.append(it)
            }
        }
        return stringBuilder.toString()!!
    }
}



@Composable
fun GreetingView(text: String) {
    Text(text = text)
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        GreetingView("Hello, Android!")
    }
}
