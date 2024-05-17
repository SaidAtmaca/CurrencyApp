package com.saidatmaca.currencyapp.presentation.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.Placeholder
import com.bumptech.glide.integration.compose.placeholder
import com.bumptech.glide.signature.ObjectKey
import com.saidatmaca.currencyapp.R
import com.saidatmaca.currencyapp.data.repository.DummyDataRepository
import com.saidatmaca.currencyapp.domain.model.Coin
import com.saidatmaca.currencyapp.presentation.ui.theme.greyColorPalette
import com.saidatmaca.currencyapp.presentation.ui.theme.mainColorPalette

@Preview(showBackground = true)
@Composable
fun CryptoRowPreview() {
    
    CryptoRow(coin = DummyDataRepository.dummyCoin, rowClicked = {})
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
        elevation = CardDefaults.cardElevation(5.dp),
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
                    .width(IntrinsicSize.Max)) {

                Image(painter = painter,
                    contentDescription ="",
                    modifier = Modifier
                        .size(60.dp)
                        .padding(5.dp)
                    )

                /*GlideImage(
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(60.dp)
                        .padding(5.dp),
                    model = coin.iconUrl ,
                    contentDescription = "",
                    loading = placeholder(R.drawable.loading_gifo),
                    failure = placeholder(R.drawable.baseline_warning_amber_24)
                ){
                    it
                        .signature(ObjectKey(coin.uuid))
                }*/
                /*AsyncImage(model = ImageRequest.Builder(LocalContext.current)
                    .data(coin.iconUrl)
                    .crossfade(true)
                    .placeholder(R.drawable.loading_gifo)
                    .build(),
                    contentDescription =   "" ,
                    modifier = Modifier
                        .padding(5.dp)
                        .size(60.dp)
                        .clickable {

                        })*/


                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(IntrinsicSize.Max)
                        .padding(start = 10.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.SpaceAround
                ) {

                    Text(text = coin.symbol, fontSize = 12.sp, color = greyColorPalette.tone50)

                    Text(text = coin.name, fontSize = 12.sp, color = Color.Black, fontWeight = FontWeight.Medium)
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
                Text(text = coin.price, fontSize = 12.sp, fontWeight = FontWeight.Medium)

                Text(text = coin.marketCap, fontSize = 8.sp)
            }


        }

    }

}