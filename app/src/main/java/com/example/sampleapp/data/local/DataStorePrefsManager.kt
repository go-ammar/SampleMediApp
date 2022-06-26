package com.example.sampleapp.data.local

import android.content.Context
import androidx.datastore.preferences.core.clear
import androidx.datastore.preferences.core.edit
import com.example.sampleapp.utils.Constants
import com.example.sampleapp.utils.Constants.USERNAME
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import com.example.sampleapp.data.local.DataStorePrefsManager.PreferencesKeys.IS_PROFILE_EXISTED
import com.example.sampleapp.data.local.DataStorePrefsManager.PreferencesKeys.USER_NAME
import com.example.sampleapp.utils.Constants.USER_PROFILE_EXISTED
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class DataStorePrefsManager @Inject constructor(@ApplicationContext context: Context) {

    private val dataStore = context.createDataStore(name = Constants.AUTO_AUTH_PREFS)

    private object PreferencesKeys {
        val USER_NAME = preferencesKey<String>(USERNAME)
        val IS_PROFILE_EXISTED = preferencesKey<Boolean>(USER_PROFILE_EXISTED)

    }

    suspend fun saveProfileStatusToDataStore(isProfileCreated: Boolean) {
        dataStore.edit { _dataStore ->
            _dataStore[IS_PROFILE_EXISTED] = isProfileCreated
        }
    }


    suspend fun getProfileStatusFromDataStore(): Boolean {
        val preferences = dataStore.data.first()
        return if (preferences[IS_PROFILE_EXISTED] == null) {
            false
        } else
            preferences[IS_PROFILE_EXISTED]!!
    }

    suspend fun saveUserName(userName: String) {
        dataStore.edit { _dataStore ->
            _dataStore[USER_NAME] = userName
        }
    }

    suspend fun getUserName(): String? {
        val preferences = dataStore.data.first()
        return preferences[USER_NAME]
    }


    suspend fun removeUser() {
        dataStore.edit {
            it.clear()
        }
    }

}