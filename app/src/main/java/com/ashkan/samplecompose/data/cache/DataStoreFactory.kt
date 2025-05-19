package com.ashkan.samplecompose.data.cache

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
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
    }

    suspend fun isTokenSaved(): Boolean {
        val preferences = context.preferenceDataStore.data.first()
        return preferences.contains(TOKEN)
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

    suspend fun getToken() = context.preferenceDataStore.data.map { it[TOKEN] }.first()

    suspend fun getRefreshToken() = context.preferenceDataStore.data.map { it[REFRESH_TOKEN] ?: "" }.first()

    suspend fun getMobileNumber() = context.preferenceDataStore.data.map { it[MOBILE_NUMBER] ?: "" }

    suspend fun clearDataStore() = context.preferenceDataStore.edit { it.clear() }
}