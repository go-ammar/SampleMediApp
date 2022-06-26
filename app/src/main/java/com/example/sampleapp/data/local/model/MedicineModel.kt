package com.example.sampleapp.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.sampleapp.data.remote.medicine.AssociatedDrug
import com.example.sampleapp.data.remote.medicine.Labs
import com.example.sampleapp.utils.TypeConverter

@Entity(tableName = "MedicineModel")
@TypeConverters(TypeConverter::class)
data class MedicineModel(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    var disease : String,
    var medicines: List<AssociatedDrug>,
    var labs: List<Labs>
    )
