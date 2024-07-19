package com.application.milsat.android.widgets

import android.provider.CalendarContract
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@Composable
fun DropDownWidgetView(menuItems: List<String>,
                       iconRes: String = "drawable/country_icon.png",
                       iconSize: Int = 25,
                       placeHolderText: String,
                       menuExpandedState: Boolean,
                       selectedIndex : Int,
                       updateMenuExpandStatus : () -> Unit,
                       onDismissMenuView : () -> Unit,
                       onMenuItemClick : (Int) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(
                onClick = {
                    updateMenuExpandStatus()
                },
            ),
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().fillMaxHeight()
        ) {

            Box(modifier = Modifier.fillMaxHeight().fillMaxWidth(0.8f), contentAlignment = Alignment.Center){

                TextComponent(
                    text =  if(selectedIndex != -1) menuItems[selectedIndex] else placeHolderText,
                    fontSize = if(selectedIndex != -1) 20 else 18,
                    textStyle = TextStyle(),
                    textColor = if(selectedIndex != -1)  Color.Yellow else  Color.Gray,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.SemiBold,
            }


        }
    }

    DropdownMenu(
        expanded = menuExpandedState,
        onDismissRequest = { onDismissMenuView() },
        modifier = Modifier
            .fillMaxWidth(0.90f)
            .background(Color.White)
    ) {
        menuItems.forEachIndexed { index, title ->
            DropdownMenuItem(
                onClick = {
                    if (index != -1) {
                        onMenuItemClick(index)
                    }
                }) {
                SubtitleTextWidget(text = title, fontSize = 20)
            }
        }
    }
}

@Composable
fun DropDownWidget(menuItems: List<String>, iconRes: String = "drawable/country_icon.png",selectedIndex: Int = -1, placeHolderText: String, iconSize: Int = 25, onMenuItemClick : (Int) -> Unit) {

    val expandedMenuItem = remember { mutableStateOf(false) }
    val selectedMenuIndex = remember { mutableStateOf(selectedIndex) }

    val modifier  = Modifier
        .padding(end = 10.dp, start = 10.dp, top = 20.dp)
        .fillMaxWidth()
        .height(60.dp)
        .border(border = BorderStroke(1.dp, color  = Color.Yellow), shape = RoundedCornerShape(15.dp))
        .background(color = Color.White, shape = RoundedCornerShape(15.dp))

    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,

        ) {
        DropDownWidgetView(
            menuItems = menuItems,
            iconRes = iconRes,
            iconSize = iconSize,
            placeHolderText = placeHolderText,
            menuExpandedState = expandedMenuItem.value,
            selectedIndex = selectedMenuIndex.value,
            updateMenuExpandStatus = {
                expandedMenuItem.value = true
            },
            onDismissMenuView = {
                expandedMenuItem.value = false
            },
            onMenuItemClick = { index ->
                selectedMenuIndex.value = index
                expandedMenuItem.value = false
                onMenuItemClick(selectedMenuIndex.value)
            }
        )
    }
}
