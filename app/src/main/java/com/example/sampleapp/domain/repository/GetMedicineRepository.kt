package com.example.sampleapp.domain.repository

import com.example.sampleapp.data.local.model.MedicineModel
import com.example.sampleapp.data.remote.medicine.Medicines
import com.example.sampleapp.data.remote.medicine.Problems
import kotlinx.coroutines.flow.Flow

interface GetMedicineRepository {

    suspend fun getMedicines(): Flow<Medicines>

    suspend fun getLocalMedicines(): List<MedicineModel>

    suspend fun setLocalMedicines(list: List<MedicineModel>)

    suspend fun doMedicinesExist() :Boolean
}