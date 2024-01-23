package com.tuna.cinergy.datasource.network.models.movieinfo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MovieInfoResponseClass (
    @SerializedName("response")
    @Expose
    val response: String,
    @SerializedName("movie_info")
    @Expose
    val movieInfo: MovieInfo,
        )

data class MovieInfo (
    @SerializedName("Title")
    @Expose
    val Title: String,
    @SerializedName("Rating")
    @Expose
    val Rating: String,
    @SerializedName("RunTime")
    @Expose
    val RunTime: String,
    @SerializedName("image_url")
    @Expose
    val image_url: String,
    @SerializedName("show_times")
    @Expose
    val showTime: List<ShowTime>,
    @SerializedName("date_list")
    @Expose
    val date_list: List<String>,
)

data class ShowTime (
    @SerializedName("date")
    @Expose
    val date: String,
    @SerializedName("sessions")
    @Expose
    val sessions: List<Sessions>,
)

data class Sessions (
    @SerializedName("Showtime")
    @Expose
    val Showtime: String
)