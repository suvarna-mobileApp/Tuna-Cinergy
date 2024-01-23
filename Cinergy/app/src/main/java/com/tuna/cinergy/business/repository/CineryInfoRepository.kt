package com.tuna.cinergy.business.repository

import com.tuna.cinergy.business.data.network.CinergyNetworkDataSource
import com.tuna.cinergy.business.domain.state.DataState
import com.tuna.cinergy.datasource.network.models.escaperoom.EscapeRoomResponseClass
import com.tuna.cinergy.datasource.network.models.guestlogin.GuestLoginResponseClass
import com.tuna.cinergy.datasource.network.models.login.LoginResponseClass
import com.tuna.cinergy.datasource.network.models.movieinfo.MovieInfoResponseClass
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CineryInfoRepository @Inject constructor(
    private val cinergyNetworkDataSource: CinergyNetworkDataSource
) {
    suspend fun guestToken(secretKey: String,
                            deviceType: String,
                            deviceId: String,
                            pushToken: String): Flow<DataState<LoginResponseClass>> = flow {
        cinergyNetworkDataSource.guestToken(secretKey,deviceType,deviceId,pushToken).collect {
            emit(it)
        }
    }

    suspend fun guestLogin(authToken: String,
                           deviceType: String,
                           deviceId: String,
                           loginType: String): Flow<DataState<GuestLoginResponseClass>> = flow {
        cinergyNetworkDataSource.guestLogin(authToken,deviceId,deviceType,loginType).collect {
            emit(it)
        }
    }

    suspend fun roomMovies(userId:String, authToken: String,deviceId: String,memberId: String,deviceType: String,locationtype: String): Flow<DataState<EscapeRoomResponseClass>> = flow {
        cinergyNetworkDataSource.roomMovies(userId,authToken,deviceId,memberId,deviceType,locationtype).collect {
            emit(it)
        }
    }

    suspend fun movieInfo(userId:String, authToken: String,deviceId: String,deviceType: String,locationtype: String,movieInfo: String): Flow<DataState<MovieInfoResponseClass>> = flow {
        cinergyNetworkDataSource.movieInfo(userId,authToken,deviceId,deviceType,locationtype,movieInfo).collect {
            emit(it)
        }
    }
}