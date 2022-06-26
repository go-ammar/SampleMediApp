package com.example.sampleapp.domain.base

import com.example.sampleapp.utils.ErrorModel


interface UseCaseResponse<Type> {

    suspend fun onSuccess(result: Any)

    suspend fun onError(errorModel: ErrorModel?)
}
