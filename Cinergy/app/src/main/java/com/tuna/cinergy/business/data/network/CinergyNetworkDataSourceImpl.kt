package com.tuna.cinergy.business.data.network

import com.tuna.cinergy.business.domain.state.DataState
import com.tuna.cinergy.datasource.network.RetrofitService
import com.tuna.cinergy.datasource.network.models.escaperoom.EscapeRoomResponseClass
import com.tuna.cinergy.datasource.network.models.guestlogin.GuestLoginResponseClass
import com.tuna.cinergy.datasource.network.models.login.LoginResponseClass
import com.tuna.cinergy.datasource.network.models.movieinfo.MovieInfoResponseClass
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CinergyNetworkDataSourceImpl @Inject constructor(
    private val retrofitService: RetrofitService,
): CinergyNetworkDataSource {

    override suspend fun guestToken(
        secretKey: String,
        deviceType: String,
        deviceId: String,
        pushToken: String
    ): Flow<DataState<LoginResponseClass>> = flow{
        retrofitService.guestToken(secretKey,deviceType,deviceId,pushToken).collect{ plans ->
            emit(plans)
        }
    }

    override suspend fun guestLogin(
        authToken: String,
        deviceId: String,
        deviceType: String,
        loginType: String
    ): Flow<DataState<GuestLoginResponseClass>> = flow{
        retrofitService.guestLogin(authToken,deviceId,deviceType,loginType).collect{ plans ->
            emit(plans)
        }
    }

    override suspend fun roomMovies(
        userId:String, authToken: String,deviceId: String,memberId: String,deviceType: String,locationtype: String
    ): Flow<DataState<EscapeRoomResponseClass>> = flow{
        retrofitService.roomMovies(userId,authToken,deviceId,memberId,deviceType,locationtype).collect{ plans ->
            emit(plans)
        }
    }

    override suspend fun movieInfo(
        userId:String, authToken: String,deviceId: String,deviceType: String,locationtype: String,movieId: String
    ): Flow<DataState<MovieInfoResponseClass>> = flow{
        retrofitService.moviesInfo(userId,authToken,deviceId,deviceType,locationtype,movieId).collect{ plans ->
            emit(plans)
        }
    }


}