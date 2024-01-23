package com.tuna.cinergy.business.domain.utils.dataStore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private const val TAG = "BasePrefManager::"

const val DataStore_NAME = "base_preferences"
val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = DataStore_NAME)

class BasePreferencesManagerImpl constructor(
    private val context: Context
) : BasePreferencesManager {

    companion object {
        val ACCESS_TOKEN = stringPreferencesKey("access_token")
    }

    override suspend fun updateAccessToken(accessToken: String) {
        context.datastore.edit { preferences ->
            preferences[ACCESS_TOKEN] = "Bearer $accessToken"
        }
    }

    override suspend fun getAccessToken() = context.datastore.data
        .catch { exeption ->
            if (exeption is IOException) {
                Log.e(TAG, "Error reading preferences: ", exeption)
                emit(emptyPreferences())
            } else {
                throw exeption
            }
        }
        .map { preferences ->
            preferences[ACCESS_TOKEN] ?: ""
        }
}