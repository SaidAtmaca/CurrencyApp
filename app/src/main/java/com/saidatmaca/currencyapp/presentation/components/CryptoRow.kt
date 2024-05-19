package com.saidatmaca.currencyapp.presentation.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import com.saidatmaca.currencyapp.R
import com.saidatmaca.currencyapp.core.common.formatChange
import com.saidatmaca.currencyapp.core.common.formatPrice
import com.saidatmaca.currencyapp.data.repository.DummyDataRepository
import com.saidatmaca.currencyapp.domain.model.Coin
import com.saidatmaca.currencyapp.presentation.ui.theme.greyColorPalette
import com.saidatmaca.currencyapp.presentation.ui.theme.mainColorPalette

@Preview(showBackground = true)
@Composable
fun CryptoRowPreview() {
    
    CryptoRow(coin = DummyDataRepository.dummyCoin, rowClicked = {}, isFav = false)
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CryptoRow(
    coin: Coin,
    isFav : Boolean,
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
        elevation = CardDefaults.cardElevation(5.dp),
        border = if (isFav)BorderStroke(1.dp, mainColorPalette.tone5) else BorderStroke(0.dp,Color.Transparent),
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
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {

            Row(
                Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .weight(1f)) {

                Image(painter = painter,
                    contentDescription ="",
                    modifier = Modifier
                        .size(60.dp)
                        .padding(5.dp)
                    )


                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .padding(start = 10.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.SpaceAround
                ) {

                    Text(text = coin.symbol,
                        fontSize = 12.sp,
                        color = greyColorPalette.tone50)

                    Text(text = coin.name,
                        fontSize = 14.sp,
                        color = mainColorPalette.tone5,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        modifier = Modifier.fillMaxWidth().padding(end = 5.dp))
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
                    color = mainColorPalette.tone5)

                Text(text = coin.change.formatChange(coin),
                    fontSize = 12.sp,
                    color = if (coin.change >= 0 ) mainColorPalette.tone7 else mainColorPalette.tone6,
                    fontWeight = FontWeight.SemiBold)
            }


        }

    }

}