package com.saidatmaca.currencyapp.presentation.components

import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saidatmaca.currencyapp.R
import com.saidatmaca.currencyapp.presentation.ui.theme.mainColorPalette


@Preview(showBackground = true)
@Composable
fun FilterComponentPreview() {

    Box(modifier = Modifier.fillMaxSize()){
        FilterComponent(
            filterSelected = {}
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterComponent(
    filterSelected : (Int) ->Unit
) {

    var isExpanded by remember {
        mutableStateOf(false)
    }

    val items = listOf(
        stringResource(id = R.string.defaultText),
        stringResource(id = R.string.price),
        stringResource(id = R.string.marketCap),
        stringResource(id = R.string.volume),
        stringResource(id = R.string.change),
        stringResource(id = R.string.listedAt),
    )

    var selectedFilter by remember {
        mutableStateOf(items.first())
    }


    ExposedDropdownMenuBox(expanded = isExpanded,
        onExpandedChange ={
            isExpanded=it
        } ) {

        Card(
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(2.dp),
            colors = CardDefaults.cardColors(containerColor = mainColorPalette.tone8),
            modifier = Modifier
                .width(minOf(100.dp))
                .menuAnchor()
                .height(intrinsicSize = IntrinsicSize.Max)

        ) {

            Row(
                Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {

                Text(text = selectedFilter,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 10.dp, top = 5.dp, bottom = 5.dp),
                    maxLines = 1,
                    fontWeight = FontWeight.SemiBold,
                    color = mainColorPalette.tone9)

                Icon(imageVector = if (isExpanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                    contentDescription ="",
                    tint = mainColorPalette.tone9,
                    modifier = Modifier
                        .size(30.dp)
                        .padding(end = 5.dp))

            }
        }

        DropdownMenu(expanded = isExpanded,
            onDismissRequest = { isExpanded=false },
            offset = DpOffset(0.dp,0.dp),
        ) {



            items.forEach { item ->
                DropdownMenuItem(text = {
                    Text(text = item, fontSize = 12.sp, color = mainColorPalette.tone9)
                },
                    onClick = {
                        selectedFilter = item
                        isExpanded=false
                        filterSelected(items.indexOf(item))
                    })
            }




        }
    }



}