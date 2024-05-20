package com.saidatmaca.currencyapp.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.saidatmaca.currencyapp.presentation.util.Navigation
import com.saidatmaca.currencyapp.presentation.ui.theme.CurrencyAppTheme

@Composable
fun CurrencyApp(
) {
    CurrencyAppTheme(dynamicColor = false) {
        val navController = rememberNavController()


        Scaffold(Modifier.fillMaxSize()) {
            Box(modifier = Modifier.padding(it)){
                Navigation(navController = navController )
            }
        }




    }
}
