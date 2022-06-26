package com.example.sampleapp.utils

import androidx.room.TypeConverter
import com.example.sampleapp.data.remote.medicine.AssociatedDrug
import com.example.sampleapp.data.remote.medicine.Labs
import com.google.gson.Gson


class TypeConverter {

    @TypeConverter
    fun listToJson(value: List<Labs>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Labs>::class.java).toList()

    @TypeConverter
    fun listToJsonObj(value: List<AssociatedDrug>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToListObj(value: String) = Gson().fromJson(value, Array<AssociatedDrug>::class.java).toList()

}