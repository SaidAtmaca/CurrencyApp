package com.saidatmaca.currencyapp.presentation.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.saidatmaca.currencyapp.R
import com.saidatmaca.currencyapp.core.common.enums.UIEvent
import com.saidatmaca.currencyapp.presentation.components.AppTopBar
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

                Text(text = "Home Screen", fontSize = 14.sp)

                Button(onClick = { viewModel.goToDetailScreen()  }) {
                    Text(text = "Detail Page")
                }

                Button(onClick = { viewModel.getAllCryptoData()  }) {
                    Text(text = "Get Cryptos")
                }

            }



            

        }
    }

    }





