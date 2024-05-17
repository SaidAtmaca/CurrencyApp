package com.saidatmaca.currencyapp.presentation.components

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.saidatmaca.currencyapp.domain.model.Coin


@Composable
fun CryptoRow(
    coin: Coin
) {

    Card(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .padding(5.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {

        Row(Modifier.fillMaxSize()) {


        }

    }

}