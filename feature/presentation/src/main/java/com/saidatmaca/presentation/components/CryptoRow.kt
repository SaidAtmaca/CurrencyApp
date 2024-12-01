package com.saidatmaca.presentation.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.saidatmaca.common.formatPrice
import com.saidatmaca.domain.formatChange
import com.saidatmaca.domain.model.Coin
import com.saidatmaca.presentation.GreyColorPalette
import com.saidatmaca.presentation.IconSizeMedium
import com.saidatmaca.presentation.MainColorPalette
import com.saidatmaca.presentation.R

@Preview(showBackground = true)
@Composable
fun CryptoRowPreview() {

    Box(modifier = Modifier.fillMaxSize().background(MainColorPalette.tone10))
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CryptoRow(
    coin: Coin,
    rowClicked :(Coin) ->Unit
) {

    Card(
        onClick = {
                  rowClicked(coin)
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .padding(5.dp),
        shape = RoundedCornerShape(5.dp),
        elevation = CardDefaults.cardElevation(0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {

        Log.e("icoUrl",coin.iconUrl.toString())

        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .decoderFactory(SvgDecoder.Factory())
                .data(coin.iconUrl)
                .placeholder(R.drawable.loading_gifo)
                .size(Size.ORIGINAL) // Set the target size to load the image at.
                .build()
        )

        Row(
            Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {

            Row(
                Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically) {

                Image(painter = painter,
                    contentDescription ="",
                    modifier = Modifier
                        .size(IconSizeMedium)
                        .padding(5.dp)
                    )


                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .padding(start = 10.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {

                    Text(text = coin.symbol,
                        fontSize = 12.sp,
                        color = GreyColorPalette.tone50,
                        fontWeight = FontWeight.Bold)

                    Text(text = coin.name,
                        fontSize = 14.sp,
                        color = MainColorPalette.tone5,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 5.dp))
                }
            }

            Column(
                Modifier
                    .fillMaxHeight()
                    .padding(end = 10.dp)
                    .width(IntrinsicSize.Max),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceAround
                ) {
                Text(text = coin.price.formatPrice(),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MainColorPalette.tone5)


                Text(text = coin.change.formatChange(coin),
                    color = if (coin.change >= 0 ) MainColorPalette.tone7 else MainColorPalette.tone6,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    //color = if (coin.change >= 0 ) mainColorPalette.tone7 else mainColorPalette.tone6,
                    )
            }


        }

    }

}