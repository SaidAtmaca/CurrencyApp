package com.saidatmaca.presentation.util

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.saidatmaca.model.Coin
import com.saidatmaca.presentation.ui.detail.DetailScreen
import com.saidatmaca.presentation.ui.home.HomeScreen


@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route,
        modifier = Modifier.fillMaxSize()){



        composable(Screen.HomeScreen.route){
            HomeScreen(navController = navController,)
        }

        composable(Screen.DetailScreen.route){
            val coin = navController.previousBackStackEntry?.savedStateHandle?.get<Coin>("coin")
            DetailScreen(
                navController = navController,
                coin = coin
            )
        }

    }
}