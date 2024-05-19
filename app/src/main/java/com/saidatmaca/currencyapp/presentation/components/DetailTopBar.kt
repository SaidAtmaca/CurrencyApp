package com.saidatmaca.currencyapp.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saidatmaca.currencyapp.presentation.ui.theme.SpaceMedium
import com.saidatmaca.currencyapp.presentation.ui.theme.SpaceSmall
import com.saidatmaca.currencyapp.presentation.ui.theme.greyColorPalette
import com.saidatmaca.currencyapp.presentation.ui.theme.mainColorPalette

@Preview(showBackground = true)
@Composable
fun DetailTopBarPrev() {

    Box(Modifier.fillMaxSize()) {

        Column(Modifier.fillMaxSize()) {
            DetailTopBar(
                onbackClicked = { /*TODO*/ },
                onFavClicked = { /*TODO*/ },
                isFavCoin = true,
                coinSymbol = "BTC",
                coinName = "Bitcoin"
            )
        }
    }

}


@Composable
fun DetailTopBar(
    onbackClicked : () -> Unit,
    onFavClicked : () ->Unit,
    isFavCoin : Boolean,
    coinSymbol : String,
    coinName : String
) {


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max),
        shape = RoundedCornerShape(0.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(
                    horizontal = SpaceSmall,
                    vertical = SpaceMedium
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){


                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription ="",
                    modifier = Modifier
                        .size(35.dp)
                        .padding(SpaceSmall)
                        .clickable {
                            onbackClicked()
                        },
                    tint = mainColorPalette.tone5)



            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = coinSymbol,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color= greyColorPalette.tone100,
                    modifier = Modifier.padding(horizontal = SpaceMedium))

                Text(text = coinName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color= mainColorPalette.tone5,
                    modifier = Modifier.padding(horizontal = SpaceMedium))
            }

            Icon(imageVector = if (isFavCoin )Icons.Filled.Notifications else Icons.Outlined.Notifications,
                contentDescription = "",
                tint = mainColorPalette.tone5,
                modifier = Modifier
                    .size(30.dp)
                    .padding(5.dp)
                    .clickable {
                        onFavClicked()
                    },)



        }
    }

}