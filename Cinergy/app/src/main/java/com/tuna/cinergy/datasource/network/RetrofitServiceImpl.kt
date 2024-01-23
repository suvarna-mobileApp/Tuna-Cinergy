package com.tuna.cinergy.datasource.network

import android.util.Log
import com.tuna.cinergy.business.domain.state.DataState
import com.tuna.cinergy.business.domain.state.ErrorUtils
import com.tuna.cinergy.datasource.network.models.escaperoom.EscapeRoomResponseClass
import com.tuna.cinergy.datasource.network.models.guestlogin.GuestLoginResponseClass
import com.tuna.cinergy.datasource.network.models.login.LoginResponseClass
import com.tuna.cinergy.datasource.network.models.movieinfo.MovieInfoResponseClass
import com.tuna.cinergy.datasource.network.retrofit.WebServices
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RetrofitServiceImpl constructor(
    private val webServices: WebServices,
) : RetrofitService {

    override suspend fun guestToken(
        secretKey: String,
        deviceType: String,
        deviceId: String,
        pushToken: String
    ): Flow<DataState<LoginResponseClass>> = flow {
        emit(DataState.Loading(true))
        try {
            val response = webServices.guestToken(secretKey,deviceType,deviceId,pushToken)
            if (response.isSuccessful) {
                Log.d("guest login::", "API isSuccessful: ")
                emit(DataState.Success(response.body()!!))
                emit(DataState.Loading(false))
            } else {
                Log.d("guest login::", "ERROR : " + response.errorBody()?.string())
                emit(DataState.Loading(false))
                emit(DataState.Error(CancellationException("unknown")))
            }
        } catch (e: Exception) {
            Log.e("Room Movies::", "Exception: $e.")
            emit(DataState.Loading(false))
            emit(DataState.Error(CancellationException("unknown")))
        }
    }

    override suspend fun guestLogin(
        authToken: String,
        deviceId: String,
        deviceType: String,
        loginType: String
    ): Flow<DataState<GuestLoginResponseClass>> = flow {
        emit(DataState.Loading(true))
        try {
            val response = webServices.guestLogin(authToken,deviceId,deviceType,loginType)
            if (response.isSuccessful) {
                Log.d("guest login::", "API isSuccessful: ")
                emit(DataState.Success(response.body()!!))
                emit(DataState.Loading(false))
            } else {
                Log.d("guest login::", "ERROR : " + response.errorBody()?.string())
                emit(DataState.Loading(false))
                emit(DataState.Error(CancellationException("unknown")))
            }
        } catch (e: Exception) {
            Log.e("Room Movies::", "Exception: $e.")
            emit(DataState.Loading(false))
            emit(DataState.Error(CancellationException("unknown")))
        }
    }

    override suspend fun roomMovies(
        userId: String,
        authToken: String,
        deviceId: String,
        memberId: String,
        deviceType: String,
        locationId: String
    ): Flow<DataState<EscapeRoomResponseClass>> = flow {
        emit(DataState.Loading(true))
        try {
            val response = webServices.roomMovies(userId,authToken,deviceId,memberId,deviceType,locationId)
            if (response.isSuccessful) {
                Log.d("Room Movies::", "API isSuccessful: ")
                emit(DataState.Success(response.body()!!))
                emit(DataState.Loading(false))
            } else {
                Log.d("Room Movies::", "ERROR : " + response.errorBody()?.string())
                emit(DataState.Loading(false))
                emit(DataState.Error(CancellationException("unknown")))
            }
        } catch (e: Exception) {
            Log.e("Room Movies::", "Exception: $e.")
            emit(DataState.Loading(false))
            emit(DataState.Error(CancellationException("unknown")))
        }
    }

    override suspend fun moviesInfo(
        userId: String,
        authToken: String,
        deviceId: String,
        deviceType: String,
        locationId: String,
        movieId:String
    ): Flow<DataState<MovieInfoResponseClass>> = flow {
        emit(DataState.Loading(true))
        try {
            val response = webServices.MovieInfo(userId,authToken,deviceId,deviceType,locationId,movieId)
            if (response.isSuccessful) {
                Log.d("Room Movies info::", "API isSuccessful: ")
                emit(DataState.Success(response.body()!!))
                emit(DataState.Loading(false))
            } else {
                Log.d("Room Movies info::", "ERROR : " + response.errorBody()?.string())
                emit(DataState.Loading(false))
                emit(DataState.Error(CancellationException("unknown")))
            }
        } catch (e: Exception) {
            Log.e("Room Movies info::", "Exception: $e.")
            emit(DataState.Loading(false))
            emit(DataState.Error(CancellationException("unknown")))
        }
    }

}