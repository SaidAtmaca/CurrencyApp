package com.saidatmaca.currencyapp.presentation.ui.detail

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.jaikeerthick.composable_graphs.composables.line.LineGraph
import com.jaikeerthick.composable_graphs.composables.line.style.LineGraphStyle
import com.jaikeerthick.composable_graphs.style.LabelPosition
import com.saidatmaca.currencyapp.R
import com.saidatmaca.currencyapp.core.common.enums.UIEvent
import com.saidatmaca.currencyapp.core.common.formatChange
import com.saidatmaca.currencyapp.core.common.formatPrice
import com.saidatmaca.currencyapp.data.repository.DummyDataRepository
import com.saidatmaca.currencyapp.domain.model.Coin
import com.saidatmaca.currencyapp.presentation.components.DetailTopBar
import com.saidatmaca.currencyapp.presentation.ui.theme.SpaceMedium
import com.saidatmaca.currencyapp.presentation.ui.theme.SpaceSmall
import com.saidatmaca.currencyapp.presentation.ui.theme.mainColorPalette
import com.saidatmaca.currencyapp.presentation.util.Screen
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {

    val navController = rememberNavController()
    DetailScreen(navController = navController,
        coin = DummyDataRepository.dummyCoin)
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

        coin?.let {
            Log.e("aboutToGoToApi",it.toString())
            viewModel.getCryptoHistoricalData(it.uuid)
        }


    }

    BackHandler(enabled = !backPressHandled) {

        navController.navigate(Screen.HomeScreen.route) {
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



    Scaffold(
        topBar = {

            DetailTopBar(
                onbackClicked = { viewModel.goToHomeScreen() },
                onFavClicked = {  },
                isFavCoin = false,
                coinSymbol = viewModel.coin.value?.symbol ?: "",
                coinName = viewModel.coin.value?.name ?: ""
            )

        }
    ) {
        Box(modifier = Modifier
            .padding(it)
            .fillMaxSize(),
            contentAlignment = Alignment.Center
        ){

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.Start) {


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {


                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(painter = painter,
                            contentDescription ="",
                            modifier = Modifier
                                .size(100.dp)
                                .padding(5.dp)
                        )

                        Text(text = viewModel.coin.value?.symbol ?: "",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color= mainColorPalette.tone4,
                            modifier = Modifier.padding(horizontal = SpaceMedium))

                        Text(text = viewModel.coin.value?.name ?: "",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color= mainColorPalette.tone5,
                            modifier = Modifier.padding(horizontal = SpaceMedium))


                    }



                }


                Text(
                    text = stringResource(id = R.string.currentPrice),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(SpaceMedium))


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Max),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {

                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.padding(SpaceMedium)){

                        Text(text = viewModel.coin.value?.price?.formatPrice() ?: "" , fontSize = 16.sp, fontWeight = FontWeight.Bold, color = mainColorPalette.tone5)

                        viewModel.coin.value?.let {
                            Text(text = it.change.formatChange(it),
                                fontSize = 12.sp,
                                color = if (it.change >= 0 ) mainColorPalette.tone7 else mainColorPalette.tone6,
                                fontWeight = FontWeight.SemiBold)
                        }

                    }


                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.padding(SpaceMedium)){

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = stringResource(id = R.string.high),
                                fontWeight = FontWeight.Medium,
                                color = mainColorPalette.tone5,
                                modifier = Modifier
                                    .padding(SpaceSmall),
                                fontSize = 12.sp)
                            Text(text = viewModel.coin.value?.price.toString(),
                                fontSize = 12.sp)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = stringResource(id = R.string.low),
                                fontWeight = FontWeight.Medium,
                                color = mainColorPalette.tone5,
                                modifier = Modifier
                                    .padding(SpaceSmall))
                            Text(text = viewModel.coin.value?.price.toString())
                        }
                    }

                }








                AnimatedVisibility(visible = viewModel.lineGraphData.isNotEmpty()) {
                    LineGraph(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        data = viewModel.lineGraphData,
                        onPointClick = {
                            val y = it.y.toDouble()
                            Toast.makeText(context, y.formatPrice(), Toast.LENGTH_SHORT).show()
                        },
                        style = LineGraphStyle(yAxisLabelPosition = LabelPosition.LEFT)
                    )
                }





            }







        }

    }
}