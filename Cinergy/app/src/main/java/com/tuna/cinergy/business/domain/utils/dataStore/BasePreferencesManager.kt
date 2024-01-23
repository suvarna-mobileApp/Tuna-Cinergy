package com.tuna.cinergy.business.domain.utils.dataStore

import kotlinx.coroutines.flow.Flow

interface BasePreferencesManager {
    suspend fun getAccessToken(): Flow<String>
    suspend fun updateAccessToken(accessToken: String)
}