package com.saidatmaca.presentation.ui.home

import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.saidatmaca.common.enums.SortValues
import com.saidatmaca.common.enums.UIEvent
import com.saidatmaca.presentation.MainColorPalette
import com.saidatmaca.presentation.R
import com.saidatmaca.presentation.util.Screen
import kotlinx.coroutines.flow.collectLatest

@Preview(showBackground = true)
@Composable
fun Prevvv() {

}

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {

    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val activity = remember { context as? ComponentActivity }
    val allCoinResponse by viewModel.allCoinResponse.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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

    AnimatedVisibility(visible = showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = { activity?.finish() }) {
                    Text(stringResource(id = R.string.yes))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(stringResource(id = R.string.no))
                }
            },
            title = {
                Text(text = stringResource(id = R.string.warning), fontSize = 20.sp)
            },
            text = {
                Text(stringResource(id = R.string.areYouSure))
            }
        )
    }



    LaunchedEffect(Unit) {
        viewModel.sortCoinList(SortValues.Default.value)
    }


    BackHandler {
        showDialog = true
    }

    Box(
        modifier = Modifier
            .background(MainColorPalette.tone10)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    )
    {


        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = stringResource(id = R.string.rankingList),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )


                com.saidatmaca.presentation.components.FilterComponent {
                    viewModel.sortCoinList(it)
                }
            }
            LazyColumn(modifier = Modifier.fillMaxSize()) {

                items(allCoinResponse.data.coins) {

                    com.saidatmaca.presentation.components.CryptoRow(
                        coin = it,
                        rowClicked = {
                            navController.currentBackStackEntry?.savedStateHandle?.set("coin", it)
                            navController.navigate(Screen.DetailScreen.route)
                        }
                    )
                }
            }


        }

        if (uiState == HomeViewModel.HomeUIState.Loading){
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color.Green,
            )
        }


    }
}





