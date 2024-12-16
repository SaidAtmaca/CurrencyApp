package com.saidatmaca.model

import java.io.Serializable

data class AllCoinResponse(
    val status: String,
    val data: Data
): Serializable
