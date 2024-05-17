package com.saidatmaca.currencyapp.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true)
@Composable
fun Prevvo() {
    LoginScaffold(topBar = {},
        bottomBar = {},
        content = {},
        modifier = Modifier.fillMaxSize())
}

@Composable
fun LoginScaffold(
    modifier: Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(modifier = modifier,
        topBar = topBar,
        bottomBar =bottomBar){innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)){

            content(innerPadding)
        }
    }
}