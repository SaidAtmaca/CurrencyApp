package com.saidatmaca.presentation.ui.detail

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.jaikeerthick.composable_graphs.composables.line.LineGraph
import com.jaikeerthick.composable_graphs.composables.line.style.LineGraphColors
import com.jaikeerthick.composable_graphs.composables.line.style.LineGraphFillType
import com.jaikeerthick.composable_graphs.composables.line.style.LineGraphStyle
import com.jaikeerthick.composable_graphs.style.LabelPosition
import com.saidatmaca.common.enums.UIEvent
import com.saidatmaca.common.formatPrice
import com.saidatmaca.domain.formatChange
import com.saidatmaca.model.Coin
import com.saidatmaca.presentation.GreyColorPalette
import com.saidatmaca.presentation.IconSizeLarge
import com.saidatmaca.presentation.MainColorPalette
import com.saidatmaca.presentation.R
import com.saidatmaca.presentation.SpaceLarge
import com.saidatmaca.presentation.SpaceMedium
import com.saidatmaca.presentation.SpaceSmall
import com.saidatmaca.presentation.util.Screen
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {

}

@Composable
fun DetailScreen(
    navController: NavController,
    coin: Coin?,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    var backPressHandled by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.setCoin(coin)
        viewModel.checkFavPosition()

        coin?.let {
            Log.e("aboutToGoToApi", it.toString())
            viewModel.getCryptoHistoricalData(it.uuid)
        }


    }

    BackHandler(enabled = !backPressHandled) {

        navController.navigate(Screen.HomeScreen.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                inclusive = false
            }
        }

        backPressHandled = true
        coroutineScope.launch {
            awaitFrame()
            onBackPressedDispatcher?.onBackPressed()
            backPressHandled = false
        }
    }

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .decoderFactory(SvgDecoder.Factory())
            .data(coin?.iconUrl)
            .placeholder(R.drawable.loading_gifo)
            .size(Size.ORIGINAL) // Set the target size to load the image at.
            .build()
    )

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UIEvent.Navigate -> {
                    navController.navigate(event.route)
                }


                else -> {}
            }
        }

    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(MainColorPalette.tone10))


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {

            com.saidatmaca.presentation.components.DetailTopBar(
                onbackClicked = { viewModel.goToHomeScreen() },
                onFavClicked = { viewModel.favClicked() },
                isFavCoin = viewModel.isFavCoin.value,
                coinSymbol = viewModel.coin.value?.symbol ?: "",
                coinName = viewModel.coin.value?.name ?: ""
            )

        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(MainColorPalette.tone10),
            contentAlignment = Alignment.Center
        ) {

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.Start
            ) {


                Column(
                    Modifier
                        .fillMaxSize()
                        .weight(0.7f)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(SpaceMedium),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    )
                    {


                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        )
                        {
                            Image(
                                painter = painter,
                                contentDescription = "",
                                modifier = Modifier
                                    .size(IconSizeLarge)
                                    .padding(5.dp)
                            )

                            Row(
                                modifier = Modifier.padding(top = SpaceSmall),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = stringResource(id = R.string.rank),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MainColorPalette.tone5,
                                    modifier = Modifier.padding(horizontal = SpaceSmall)
                                )

                                Text(
                                    text = viewModel.coin.value?.rank.toString(),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = MainColorPalette.tone5
                                )
                            }



                            Text(
                                text = viewModel.coin.value?.symbol ?: "",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = GreyColorPalette.tone100,
                                modifier = Modifier.padding(horizontal = SpaceMedium)
                            )

                            Text(
                                text = viewModel.coin.value?.name ?: "",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = MainColorPalette.tone5,
                                modifier = Modifier.padding(horizontal = SpaceMedium)
                            )


                        }


                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .weight(1f),
                        verticalArrangement = Arrangement.Center
                    ) {

                        Row(Modifier.fillMaxWidth()) {
                            Text(
                                text = stringResource(id = R.string.currentPrice),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = MainColorPalette.tone5,
                                modifier = Modifier.padding(horizontal = SpaceMedium)
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(IntrinsicSize.Max),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        )
                        {

                            Column(
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.padding(
                                    horizontal = SpaceMedium,
                                    vertical = SpaceSmall
                                )
                            ) {

                                Text(
                                    text = viewModel.coin.value?.price?.formatPrice() ?: "",
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MainColorPalette.tone5,
                                    modifier = Modifier.padding(1.dp)
                                )

                                viewModel.coin.value?.let {
                                    Text(
                                        text = it.change.formatChange(it),
                                        fontSize = 14.sp,
                                        color = if (it.change >= 0) MainColorPalette.tone7 else MainColorPalette.tone6,
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier.padding(1.dp)
                                    )
                                }

                            }


                            Column(
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.padding(
                                    horizontal = SpaceMedium,
                                    vertical = SpaceSmall
                                )
                            ) {

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(1.dp)
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.high),
                                        fontWeight = FontWeight.Bold,
                                        color = MainColorPalette.tone5,
                                        fontSize = 14.sp
                                    )

                                    Text(
                                        text = viewModel.highestPrice.value.formatPrice(),
                                        fontSize = 14.sp,
                                        color = if (viewModel.highestPrice.value >= viewModel.coin.value?.price ?: 0.0) MainColorPalette.tone7 else MainColorPalette.tone6
                                    )
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(1.dp)
                                ) {

                                    Text(
                                        text = stringResource(id = R.string.low),
                                        fontWeight = FontWeight.Bold,
                                        color = MainColorPalette.tone5,
                                        fontSize = 14.sp
                                    )

                                    Text(
                                        text = viewModel.lowestPrice.value.formatPrice(),
                                        fontSize = 14.sp,
                                        color = if (viewModel.lowestPrice.value < viewModel.coin.value?.price ?: 0.0) MainColorPalette.tone6 else MainColorPalette.tone7
                                    )
                                }
                            }

                        }
                    }

                }

                Column(
                    Modifier
                        .fillMaxSize()
                        .weight(0.3f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    AnimatedVisibility(visible = viewModel.lineGraphData.isNotEmpty()) {
                        LineGraph(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(SpaceLarge),
                            data = viewModel.lineGraphData,
                            onPointClick = {
                                val y = it.y.toDouble()
                                Toast.makeText(context, y.formatPrice(), Toast.LENGTH_SHORT).show()
                            },
                            style = LineGraphStyle(
                                yAxisLabelPosition = LabelPosition.LEFT,
                                colors = LineGraphColors(
                                    lineColor = MainColorPalette.tone5,
                                    pointColor = MainColorPalette.tone5,
                                    fillType = LineGraphFillType
                                        .Gradient(
                                            Brush.verticalGradient(
                                                listOf(MainColorPalette.tone8, Color.White)
                                            )
                                        )

                                )
                            )
                        )
                    }

                }


            }


        }

    }
}