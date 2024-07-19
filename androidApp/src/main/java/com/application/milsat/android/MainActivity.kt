package com.application.milsat.android

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.room.Room
import com.application.milsat.android.Enum.UI_TYPE
import com.application.milsat.android.Model.FieldComponent
import com.application.milsat.android.Room.AppDatabase
import com.application.milsat.android.Room.Form
import com.application.milsat.android.widgets.ButtonComponent
import com.application.milsat.android.widgets.DropDownWidget
import com.application.milsat.android.widgets.TextComponent
import com.application.milsat.android.widgets.TextFieldComponent
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "form"
        ).allowMainThreadQueries()
            .build()

        setContent {

            var buuildingName = remember { mutableStateOf("") }
            var address = remember { mutableStateOf( "") }
            var ownership = remember { mutableStateOf( "") }
            var status = remember { mutableStateOf( "") }
            var ownerFullname = remember { mutableStateOf( "") }
            var ownerPhone = remember { mutableStateOf( "") }
            var uses = remember { mutableStateOf( "") }


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
                    uiList.add(ownersPhone(formFields))
                    uiList.add(buildingUses(formFields))


                    Column(modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.50f), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {


                        Row(modifier = Modifier.fillMaxWidth()) {

                            Box(modifier = Modifier
                                .weight(1f)
                                .padding(20.dp)
                                .height(70.dp), contentAlignment = Alignment.CenterStart) {
                                ButtonComponent(
                                    modifier = Modifier.fillMaxWidth(0.40f),
                                    buttonText = "Export to CSV"
                                ) {
                                    val formDao = db.formDao()
                                    val formList =  formDao.getAll()
                                    ActivityCompat.requestPermissions(
                                        this@MainActivity,
                                        arrayOf<String>(READ_EXTERNAL_STORAGE), 0)
                                    val folder =
                                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                                    val file = File(folder, "data.csv")
                                    csvWriter().writeAll(listOf(formList), file.outputStream())

                                    Toast.makeText(
                                        this@MainActivity,
                                        "CSV Saved",
                                        Toast.LENGTH_LONG
                                    ).show()

                                }
                            }

                            Box(modifier = Modifier
                                .weight(1f)
                                .padding(20.dp)
                                .height(70.dp), contentAlignment = Alignment.CenterEnd) {
                                ButtonComponent(
                                    modifier = Modifier.fillMaxWidth(0.40f),
                                    buttonText = "Sync to Cloud"
                                ) {

                                }
                            }
                        }




                        uiList.forEach { it2 ->
                            if (it2.uiType == UI_TYPE.TEXT_FIELD.toPath()){
                                Box(modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp)
                                    .border(
                                        width = 1.dp,
                                        shape = RoundedCornerShape(10.dp),
                                        color = Color.Black
                                    )
                                    .height(90.dp), contentAlignment = Alignment.Center) {

                                    if (it2.columnName == "NAME_BLD") {
                                        Column(modifier = Modifier.padding(start = 20.dp)) {
                                            TextComponent(
                                                text = "Building Name",
                                                fontSize = 20)
                                            TextFieldComponent(text = buuildingName.value,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(50.dp)
                                                    .padding(start = 20.dp)
                                                    .width(200.dp),
                                                onValueChange = {
                                                    if (it!!.length < it2.maxLength) {
                                                        buuildingName.value = it
                                                    }
                                                })
                                        }
                                    }

                                   else if (it2.columnName == "ADDRESS") {
                                        Column(modifier = Modifier.padding(start = 20.dp)) {
                                            TextComponent(
                                                text = "Address",
                                                fontSize = 20
                                            )
                                            TextFieldComponent(text = address.value,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(50.dp)
                                                    .padding(start = 20.dp)
                                                    .width(200.dp),
                                                onValueChange = {
                                                    if (it!!.length < it2.maxLength) {
                                                        address.value = it
                                                    }
                                                })
                                        }
                                    }
                                    else if (it2.columnName == "NAM_OWN") {
                                        Column(modifier = Modifier.padding(start = 20.dp)) {
                                            TextComponent(
                                                text = "Owner Name",
                                                fontSize = 20
                                            )
                                            TextFieldComponent(text = ownerFullname.value,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(50.dp)
                                                    .width(200.dp),
                                                onValueChange = {
                                                    if (it!!.length < it2.maxLength) {
                                                        ownerFullname.value = it
                                                    }
                                                })
                                        }
                                    }
                                    else if (it2.columnName == "NUM_OWN") {
                                        Column(modifier = Modifier.padding(start = 20.dp)) {
                                            TextComponent(
                                                text = "Owner Phone",
                                                fontSize = 20
                                            )
                                            TextFieldComponent(text = ownerPhone.value,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(50.dp)
                                                    .width(200.dp),
                                                onValueChange = {
                                                    if (it!!.length < it2.maxLength) {
                                                        ownerPhone.value = it
                                                    }
                                                })
                                        }
                                    }

                                }
                            }
                            else if (it2.uiType == UI_TYPE.DROP_DOWN.toPath()){
                             if (it2.columnName == "OWNER") {
                                 Column(modifier = Modifier.padding(start = 20.dp)) {
                                     TextComponent(text = "Building Ownership", fontSize = 20)
                                     Box(
                                         modifier = Modifier
                                             .fillMaxWidth()
                                             .padding(end = 20.dp, top = 20.dp, bottom = 20.dp)
                                             .border(
                                                 width = 1.dp,
                                                 shape = RoundedCornerShape(10.dp),
                                                 color = Color.Black
                                             )
                                             .height(70.dp), contentAlignment = Alignment.Center
                                     ) {
                                         DropDownWidget(
                                             menuItems = it2.dropDownValues,
                                             placeHolderText = ownership.value,
                                             onMenuItemClick = {
                                                 ownership.value = it2.dropDownValues[it]
                                             }
                                         )
                                     }
                                 }
                             }
                                if (it2.columnName == "BLD_STAT") {
                                    Column(modifier = Modifier.padding(start = 20.dp)) {
                                        TextComponent(text = "Building Status", fontSize = 20)
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(end = 20.dp, top = 20.dp, bottom = 20.dp)
                                                .border(
                                                    width = 1.dp,
                                                    shape = RoundedCornerShape(10.dp),
                                                    color = Color.Black
                                                )
                                                .height(70.dp), contentAlignment = Alignment.Center
                                        ) {
                                            DropDownWidget(
                                                menuItems = it2.dropDownValues,
                                                placeHolderText = status.value,
                                                onMenuItemClick = {
                                                    status.value = it2.dropDownValues[it]
                                                }
                                            )
                                        }
                                    }
                                }
                                if (it2.columnName == "BLD_USE") {
                                    Column(modifier = Modifier.padding(start = 20.dp)) {
                                        TextComponent(text = "Building Uses", fontSize = 20)
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(end = 20.dp, top = 20.dp, bottom = 20.dp)
                                                .border(
                                                    width = 1.dp,
                                                    shape = RoundedCornerShape(10.dp),
                                                    color = Color.Black
                                                )
                                                .height(70.dp), contentAlignment = Alignment.Center
                                        ) {
                                            DropDownWidget(
                                                menuItems = it2.dropDownValues,
                                                placeHolderText = uses.value,
                                                onMenuItemClick = {
                                                    uses.value = it2.dropDownValues[it]
                                                }
                                            )
                                        }

                                    }
                                }
                            }
                        }

                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                            .height(70.dp), contentAlignment = Alignment.Center) {
                            ButtonComponent(
                                modifier = Modifier.fillMaxWidth(),
                                buttonText = "Save"
                            ) {
                                if (buuildingName.value.isEmpty()) {
                                    Toast.makeText(
                                        this@MainActivity,
                                        "Name of Building is Required",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                                else{
                                    val form = Form(buildingName = buuildingName.value, address = address.value, owner = ownerFullname.value,
                                        buildingStatus = status.value, ownersPhone = ownerPhone.value, ownersFullName = ownerFullname.value, buildingUses = uses.value)
                                        val formDao = db.formDao()
                                        formDao.insertAll(form)
                                    Toast.makeText(
                                        this@MainActivity,
                                        "Saved Successfully",
                                        Toast.LENGTH_LONG
                                    ).show()

                                }

                            }
                        }

                    }
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
