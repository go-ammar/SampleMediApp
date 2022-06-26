package com.example.sampleapp.presentation.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sampleapp.base.BaseViewModel
import com.example.sampleapp.data.local.model.MedicineModel
import com.example.sampleapp.data.remote.medicine.Medicines
import com.example.sampleapp.data.remote.medicine.Problems
import com.example.sampleapp.domain.base.UseCaseResponse
import com.example.sampleapp.domain.usecase.GetMedicineUseCase
import com.example.sampleapp.utils.ErrorModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMedicineUseCase: GetMedicineUseCase
) : BaseViewModel() {

    init {
        getMedicine()
    }

    private var _listLocal = MutableLiveData<List<MedicineModel>>()
    var listLocal: LiveData<List<MedicineModel>> = _listLocal

    private val _listApi = MutableStateFlow<Medicines?>(null)
    val listApi: StateFlow<Medicines?> = _listApi


    val error = MutableLiveData(false)

    private fun getMedicine() {


        getMedicineUseCase.invoke(scope = scope,
            parameter = null,
            onResult = object : UseCaseResponse<Medicines> {
                override suspend fun onSuccess(result: Any) {

                    _listApi.value = result as Medicines?
                }

                override suspend fun onError(errorModel: ErrorModel?) {

                    doExist()

                }
            })

    }

    private fun doExist() {
        scope.launch(Dispatchers.IO) {
            if (getMedicineUseCase.doMedicinesExist()) {
                getFlow()
            } else {
                error.postValue(true)
            }

        }
    }

    fun getFlow() = flow {
        emit(getMedicineUseCase.getLocalMedicines())
    }

    fun setLocalMedicines(list: List<MedicineModel>) {
        scope.launch(Dispatchers.IO) {
            getMedicineUseCase.setLocalMedicines(list)
        }
    }


}
