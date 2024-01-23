package com.tuna.cinergy.datasource.network.retrofit

import com.tuna.cinergy.datasource.network.models.escaperoom.EscapeRoomResponseClass
import com.tuna.cinergy.datasource.network.models.guestlogin.GuestLoginResponseClass
import com.tuna.cinergy.datasource.network.models.login.LoginResponseClass
import com.tuna.cinergy.datasource.network.models.movieinfo.MovieInfoResponseClass
import retrofit2.Response
import retrofit2.http.*

interface WebServices {

    @POST("guestToken")
    suspend fun guestToken(
        @Query("secret_key") secretKey: String,
        @Query("device_type") deviceType: String,
        @Query("device_id") deviceId: String,
        @Query("push_token") pushToken: String,
    ): Response<LoginResponseClass>

    @POST("login")
    suspend fun guestLogin(
        @Header("Authorization") authToken: String,
        @Query("device_id") deviceId: String,
        @Query("device_type") deviceType: String,
        @Query("login_type") loginType: String,
    ): Response<GuestLoginResponseClass>

    @POST("escapeRoomMovies")
    suspend fun roomMovies(
        @Header("userid") userid: String,
        @Header("Authorization") authToken: String,
        @Query("device_id") deviceId: String,
        @Query("member_id") memberId: String,
        @Query("device_type") deviceType: String,
        @Query("location_id") locationId: String,
    ): Response<EscapeRoomResponseClass>

    @POST("getMovieInfo")
    suspend fun MovieInfo(
        @Header("userid") userid: String,
        @Header("Authorization") authToken: String,
        @Query("device_id") deviceId: String,
        @Query("device_type") deviceType: String,
        @Query("location_id") locationId: String,
        @Query("movie_id") movieId: String,
    ): Response<MovieInfoResponseClass>
}