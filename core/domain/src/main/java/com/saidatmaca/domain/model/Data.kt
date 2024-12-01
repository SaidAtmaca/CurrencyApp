package com.saidatmaca.domain.model

import java.io.Serializable

data class Data(
    val stats: Stats,
    val coins: List<Coin>
) : Serializable
