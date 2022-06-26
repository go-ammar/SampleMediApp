package com.example.sampleapp.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.cancel

abstract class BaseViewModel : ViewModel() {

    val scope = viewModelScope

    // Cancel the job when the view model is destroyed
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}