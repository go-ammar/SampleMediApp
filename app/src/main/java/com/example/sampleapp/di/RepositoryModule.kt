package com.example.sampleapp.di

import com.example.sampleapp.data.local.dao.MedicinesDao
import com.example.sampleapp.data.remote.apiservice.ApiNetworkService
import com.example.sampleapp.domain.repository.GetMedicineRepository
import com.example.sampleapp.domain.repository.GetMedicineRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMedicineRepository(
        apiNetworkService: ApiNetworkService,
        dao: MedicinesDao
    ): GetMedicineRepository {
        return GetMedicineRepositoryImpl(
            apiNetworkService = apiNetworkService,
            dao = dao
        )
    }


}