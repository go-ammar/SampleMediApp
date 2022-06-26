package com.example.sampleapp.di

import android.content.Context
import androidx.room.Room
import com.example.sampleapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDb(@ApplicationContext context: Context) = Room
        .databaseBuilder(context, AppDatabase::class.java, Constants.DATABASE_NAME)
        .allowMainThreadQueries()
        .enableMultiInstanceInvalidation() // maybe room database create multi instance. add this line solved my problem
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideFeedbackDao(db: AppDatabase) = db.getMedicinesDao()

}