package com.tuna.cinergy.business.data.network

import com.tuna.cinergy.business.domain.state.DataState
import com.tuna.cinergy.datasource.network.models.escaperoom.EscapeRoomResponseClass
import com.tuna.cinergy.datasource.network.models.guestlogin.GuestLoginResponseClass
import com.tuna.cinergy.datasource.network.models.login.LoginResponseClass
import com.tuna.cinergy.datasource.network.models.movieinfo.MovieInfoResponseClass
import kotlinx.coroutines.flow.Flow

interface CinergyNetworkDataSource {
    suspend fun guestToken(secretKey: String,deviceType: String,deviceId: String,pushToken: String): Flow<DataState<LoginResponseClass>>

    suspend fun guestLogin(authToken: String,deviceId: String,deviceType: String,loginType: String): Flow<DataState<GuestLoginResponseClass>>

    suspend fun roomMovies(userId:String, authToken: String,deviceId: String,memberId: String,deviceType: String,locationtype: String): Flow<DataState<EscapeRoomResponseClass>>

    suspend fun movieInfo(userId:String, authToken: String,deviceId: String,deviceType: String,locationtype: String,movieId: String): Flow<DataState<MovieInfoResponseClass>>
}