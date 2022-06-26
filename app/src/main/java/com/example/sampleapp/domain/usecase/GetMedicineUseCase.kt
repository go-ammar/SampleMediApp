package com.example.sampleapp.domain.usecase

import com.example.sampleapp.data.local.model.MedicineModel
import com.example.sampleapp.data.remote.medicine.Medicines
import com.example.sampleapp.domain.base.BaseUseCase
import com.example.sampleapp.domain.repository.GetMedicineRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMedicineUseCase @Inject constructor(
    private val medicineRepository: GetMedicineRepository
) : BaseUseCase<Medicines>() {

    override suspend fun run(parameter: Any?): Flow<Medicines> {
        return medicineRepository.getMedicines()
    }

    suspend fun getLocalMedicines(): List<MedicineModel> {
        return medicineRepository.getLocalMedicines()
    }

    suspend fun setLocalMedicines(list: List<MedicineModel>) {
        medicineRepository.setLocalMedicines(list)
    }

    suspend fun doMedicinesExist() : Boolean {
        return medicineRepository.doMedicinesExist()
    }



}