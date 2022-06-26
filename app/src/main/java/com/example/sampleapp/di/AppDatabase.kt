package com.example.sampleapp.di

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.sampleapp.data.local.dao.MedicinesDao
import com.example.sampleapp.data.local.model.MedicineModel
import com.example.sampleapp.utils.Constants

@Database(entities = [MedicineModel::class], version = 1 , exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getMedicinesDao(): MedicinesDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, Constants.DATABASE_NAME)
                .allowMainThreadQueries()
                .enableMultiInstanceInvalidation() // maybe room database create multi instance. add this line solved my problem
                .fallbackToDestructiveMigration()
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                    }
                })
                .build()
        }
    }

}
