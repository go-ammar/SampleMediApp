package com.example.sampleapp.data.local.dao

import androidx.room.*
import androidx.room.Dao
import com.example.sampleapp.data.local.model.MedicineModel

@Dao
interface MedicinesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedicines(medicines: List<MedicineModel>)

    @Query("DELETE FROM MedicineModel")
    fun deleteAllMedicines()

    @Transaction
    suspend fun upsertMedicines(medicines: List<MedicineModel>) {
        deleteAllMedicines()
        insertMedicines(medicines)
    }

    @Query("Select * from MedicineModel")
    suspend fun getAllMedicines(): List<MedicineModel>

    @Query("SELECT EXISTS(SELECT * FROM MedicineModel)")
    suspend fun doMedicinesExist(): Boolean

}