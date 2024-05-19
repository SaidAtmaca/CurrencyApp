package com.saidatmaca.currencyapp.presentation.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.saidatmaca.currencyapp.R
import com.saidatmaca.currencyapp.core.common.enums.SortValues
import com.saidatmaca.currencyapp.core.common.enums.UIEvent
import com.saidatmaca.currencyapp.presentation.components.AppTopBar
import com.saidatmaca.currencyapp.presentation.components.CryptoRow
import com.saidatmaca.currencyapp.presentation.components.FilterComponent
import com.saidatmaca.currencyapp.presentation.util.Screen
import kotlinx.coroutines.flow.collectLatest

@Preview(showBackground = true)
@Composable
fun Prevvv() {

}

@Composable
fun HomeScreen(navController: NavController,
               viewModel: HomeViewModel = hiltViewModel()) {



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


    LaunchedEffect(Unit) {
        viewModel.sortCoinList(SortValues.Default.value)
    }


    /** Api key */
    // coinrankingea8fbbc917a3c8a7ff1952a072dfd0f47a14559e79cd2908
    // https://api.coinranking.com/v2

    Scaffold(modifier = Modifier
        .fillMaxSize(),
        topBar = {

                 AppTopBar(
                     title = stringResource(id = R.string.homeScreen),
                     isMainScreen = true ,
                     backClicked = {

                     })
        }) {

        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            contentAlignment = Alignment.Center){


            Column(Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween) {

                    Text(text = stringResource(id = R.string.rankingList))


                    FilterComponent {
                        viewModel.sortCoinList(it)
                    }
                }

               /* Text(text = "Home Screen", fontSize = 14.sp)

                Button(onClick = { viewModel.goToDetailScreen()  }) {
                    Text(text = "Detail Page")
                }

                Button(onClick = { viewModel.getAllCryptoData()  }) {
                    Text(text = "Get Cryptos")
                }*/
                LazyColumn(modifier = Modifier.fillMaxSize()) {

                    items(viewModel.coins.value){

                        val isFav= viewModel.checkFavCard(it)
                        CryptoRow(
                            coin = it,
                            isFav = isFav,
                            rowClicked = {
                                navController.currentBackStackEntry?.savedStateHandle?.set("coin",it)
                                navController.navigate(Screen.DetailScreen.route)
                            }
                        )
                    }
                }


            }
            
            




            

        }
    }

    }





