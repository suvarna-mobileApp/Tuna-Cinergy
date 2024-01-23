package com.tuna.cinergy.datasource.network

import com.tuna.cinergy.business.domain.state.DataState
import com.tuna.cinergy.datasource.network.models.escaperoom.EscapeRoomResponseClass
import com.tuna.cinergy.datasource.network.models.guestlogin.GuestLoginResponseClass
import com.tuna.cinergy.datasource.network.models.login.LoginResponseClass
import com.tuna.cinergy.datasource.network.models.movieinfo.MovieInfoResponseClass
import kotlinx.coroutines.flow.Flow

interface RetrofitService {
    suspend fun guestToken(secretKey: String,deviceType: String,deviceId: String,pushToken: String): Flow<DataState<LoginResponseClass>>

    suspend fun guestLogin(authToken: String,deviceType: String,deviceId: String,loginType: String): Flow<DataState<GuestLoginResponseClass>>

    suspend fun roomMovies(userId:String, authToken: String,deviceId: String,memberId: String,deviceType: String,locationtype: String): Flow<DataState<EscapeRoomResponseClass>>

    suspend fun moviesInfo(userId:String, authToken: String,deviceId: String,deviceType: String,locationtype: String,movieId: String): Flow<DataState<MovieInfoResponseClass>>
}