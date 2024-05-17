package com.saidatmaca.currencyapp.presentation.ui.detail

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
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.saidatmaca.currencyapp.R
import com.saidatmaca.currencyapp.core.common.enums.UIEvent
import com.saidatmaca.currencyapp.data.repository.DummyDataRepository
import com.saidatmaca.currencyapp.domain.model.Coin
import com.saidatmaca.currencyapp.presentation.components.AppTopBar
import com.saidatmaca.currencyapp.presentation.components.DetailTopBar
import com.saidatmaca.currencyapp.presentation.ui.theme.SpaceMedium
import com.saidatmaca.currencyapp.presentation.ui.theme.SpaceSmall
import com.saidatmaca.currencyapp.presentation.ui.theme.mainColorPalette
import kotlinx.coroutines.flow.collectLatest

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


    LaunchedEffect(Unit) {
        viewModel.setCoin(coin)
    }

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

                        Text(text = viewModel.coin.value?.price ?: stringResource(id = R.string.loading), fontSize = 16.sp)
                        Text(text = viewModel.coin.value?.marketCap ?: stringResource(id = R.string.loading), fontSize = 12.sp)
                    }


                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.padding(SpaceMedium)){

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = stringResource(id = R.string.high),
                                fontWeight = FontWeight.Medium,
                                color = mainColorPalette.tone5,
                                modifier = Modifier
                                    .padding(SpaceSmall))
                            Text(text = viewModel.coin.value?.price ?: stringResource(id = R.string.loading))
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = stringResource(id = R.string.low),
                                fontWeight = FontWeight.Medium,
                                color = mainColorPalette.tone5,
                                modifier = Modifier
                                    .padding(SpaceSmall))
                            Text(text = viewModel.coin.value?.price ?: stringResource(id = R.string.loading))
                        }
                    }

                }





            }







        }

    }
}