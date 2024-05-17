package com.saidatmaca.currencyapp.presentation.ui.detail

import androidx.compose.foundation.layout.Box
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.saidatmaca.currencyapp.R
import com.saidatmaca.currencyapp.core.common.enums.UIEvent
import com.saidatmaca.currencyapp.presentation.components.AppTopBar
import kotlinx.coroutines.flow.collectLatest


@Composable
fun DetailScreen(
    navController: NavController,
    viewModel: DetailViewModel = hiltViewModel()
) {


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
            AppTopBar(
                title = stringResource(id = R.string.detailScreen),
                isMainScreen = false ,
                backClicked = {
                        viewModel.goToHomeScreen()
                })
        }
    ) {
        Box(modifier = Modifier
            .padding(it)
            .fillMaxSize(),
            contentAlignment = Alignment.Center
        ){



            Text(text = "Detail Page")



        }

    }
}