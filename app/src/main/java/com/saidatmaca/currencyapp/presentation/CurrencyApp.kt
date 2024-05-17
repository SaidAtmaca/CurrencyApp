package com.saidatmaca.currencyapp.presentation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.saidatmaca.currencyapp.presentation.util.Screen
import com.saidatmaca.currencyapp.presentation.util.Navigation
import com.saidatmaca.currencyapp.ui.theme.CurrencyAppTheme

@Composable
fun CurrencyApp(
) {
    CurrencyAppTheme {
        val navController = rememberNavController()

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val drawerMenuEnabled  = navBackStackEntry?.destination?.route in listOf(
            Screen.HomeScreen.route
        )
        val drawerState =rememberDrawerState(DrawerValue.Closed)

        val scope = rememberCoroutineScope()




        ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet {
                    Spacer(Modifier.height(20.dp))



                }
            },
            drawerState = drawerState
           ,
            // Only enable opening the drawer via gestures if the screen is not expanded
            gesturesEnabled = drawerMenuEnabled
          
        ) {
            Row {
               Navigation(navController = navController,drawerState )
            }
        }
    }
}
