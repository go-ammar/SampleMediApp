package com.example.sampleapp.presentation.login

import androidx.lifecycle.MutableLiveData
import com.example.sampleapp.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor() : BaseViewModel(){

    val email = MutableLiveData("test")
    val pwd = MutableLiveData("hello")

    fun getEmailLength(): Boolean {
        if (email.value?.length!! > 5) {
            return true
        }
        return false
    }

    fun getPwdLength(): Boolean {
        if (pwd.value?.length!! > 5) {
            return true
        }
        return false
    }

}