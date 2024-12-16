package com.saidatmaca.core.viewmodel

import ViewModelKey
import ViewModelStateFlow
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job

open class BaseViewModel : ViewModel() {

    var job : Job? = null

    protected val key: ViewModelKey = ViewModelKey(this::class.java.name)

    protected fun <T> BaseViewModel.viewModelStateFlow(value: T): ViewModelStateFlow<T> {
        return ViewModelStateFlow(key = key, value = value)
    }

}