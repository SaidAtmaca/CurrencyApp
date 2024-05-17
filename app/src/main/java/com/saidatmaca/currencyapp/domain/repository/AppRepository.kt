package com.saidatmaca.currencyapp.domain.repository

import com.saidatmaca.currencyapp.core.utils.Resource
import com.saidatmaca.currencyapp.data.local.entity.User
import com.saidatmaca.currencyapp.domain.model.ApiResponse
import kotlinx.coroutines.flow.Flow


interface AppRepository {


    fun getAllCryptoData() : Flow<Resource<ApiResponse>>



}