package com.example.sampleapp.domain.repository

import com.example.sampleapp.data.local.dao.MedicinesDao
import com.example.sampleapp.data.local.model.MedicineModel
import com.example.sampleapp.data.remote.apiservice.ApiNetworkService
import com.example.sampleapp.data.remote.medicine.Medicines
import com.example.sampleapp.data.remote.medicine.Problems
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetMedicineRepositoryImpl @Inject constructor(
    private val apiNetworkService: ApiNetworkService,
    private val dao : MedicinesDao
): GetMedicineRepository {

    override suspend fun getMedicines(): Flow<Medicines> {
        return flow {
            emit(apiNetworkService.getMedicines())
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getLocalMedicines(): List<MedicineModel> {
        return dao.getAllMedicines()
    }

    override suspend fun setLocalMedicines(list: List<MedicineModel>) {
        dao.upsertMedicines(list)
    }

    override suspend fun doMedicinesExist() :Boolean {
        return dao.doMedicinesExist()
    }

}