package com.saidatmaca.currencyapp.core.utils


data class ResponseResult<T>(
    val data: List<T>,
    val isError: Boolean,
    val errorText : String = ""
)