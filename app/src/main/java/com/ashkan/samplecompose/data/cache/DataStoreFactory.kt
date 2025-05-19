package com.ashkan.samplecompose.data.cache

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

const val DATA_STORE_NAME = "sampleCompose_preference"

val Context.preferenceDataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)

class DataStoreManager(val context: Context) {
    companion object {

        /**
         *holds the keys to store and retrieve the data
         **/
        val TOKEN = stringPreferencesKey("TOKEN")
        val REFRESH_TOKEN = stringPreferencesKey("REFRESH_TOKEN")
        val MOBILE_NUMBER = stringPreferencesKey("MOBILE_NUMBER")
        val UPDATE_AVAILABLE = booleanPreferencesKey("UPDATE_AVAILABLE")
        val FORCE_UPDATE = booleanPreferencesKey("FORCE_UPDATE")
    }

    suspend fun saveToken(token: String) {
        context.preferenceDataStore.edit {
            it[TOKEN] = token
        }
    }

    suspend fun saveRefreshToken(refreshToken: String) {
        context.preferenceDataStore.edit {
            it[REFRESH_TOKEN] = refreshToken
        }
    }

    suspend fun saveUpdateAvailable(updateAvailable : Boolean) {
        context.preferenceDataStore.edit {
            it[UPDATE_AVAILABLE] = updateAvailable
        }
    }

    suspend fun saveForceUpdate(forceUpdate : Boolean) {
        context.preferenceDataStore.edit {
            it[FORCE_UPDATE] = forceUpdate
        }
    }

    suspend fun getToken() = context.preferenceDataStore.data.map { it[TOKEN] ?: "" }.first()

    suspend fun getRefreshToken() = context.preferenceDataStore.data.map { it[REFRESH_TOKEN] ?: "" }.first()

    suspend fun getMobileNumber() = context.preferenceDataStore.data.map { it[MOBILE_NUMBER] ?: "" }

    suspend fun getUpdateAvailable() = context.preferenceDataStore.data.map { it[UPDATE_AVAILABLE] == true }.first()

    suspend fun getForceUpdate() = context.preferenceDataStore.data.map { it[FORCE_UPDATE] == true }.first()

    suspend fun clearDataStore() = context.preferenceDataStore.edit { it.clear() }
}