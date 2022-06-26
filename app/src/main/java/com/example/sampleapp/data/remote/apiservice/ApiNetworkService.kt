package com.example.sampleapp.data.remote.apiservice

import com.example.sampleapp.data.remote.medicine.Medicines
import retrofit2.http.GET

interface ApiNetworkService {

    @GET("8d5ed05d-cb93-4f55-89cb-876214b6c9f5")
    suspend fun getMedicines(): Medicines

}