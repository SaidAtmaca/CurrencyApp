package com.saidatmaca.currencyapp.domain.model

import java.io.Serializable

data class ApiResponse(
    val status: String,
    val data: Data
): Serializable
