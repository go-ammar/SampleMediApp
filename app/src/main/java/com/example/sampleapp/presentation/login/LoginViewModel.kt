package com.example.sampleapp.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sampleapp.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor() : BaseViewModel(){

    val email = MutableLiveData("")
    val pwd = MutableLiveData("")

//    private val passwordLength = MutableLiveData(false)
//    val _passwordLength : LiveData<Boolean> = passwordLength
//
//    val emailLength = MutableLiveData(false)
//    val _emailLength : LiveData<Boolean> = emailLength

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