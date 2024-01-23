package com.tuna.cinergy.datasource.network.models.escaperoom

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class EscapeRoomResponseClass (
    @SerializedName("response")
    @Expose
    val response: String,
    @SerializedName("er_tickets")
    @Expose
    val er_tickets: String,
    @SerializedName("escape_rooms_movies")
    @Expose
    val escape_rooms_movies: List<EscapeRoom>,
)

data class EscapeRoom (
    @SerializedName("ID")
    @Expose
    val ID: String,
    @SerializedName("ScheduledFilmId")
    @Expose
    val ScheduledFilmId: String,
    @SerializedName("slug")
    @Expose
    val slug: String,
    @SerializedName("Title")
    @Expose
    val Title: String,
    @SerializedName("Rating")
    @Expose
    val Rating: String,
    @SerializedName("RunTime")
    @Expose
    val RunTime: String,
    @SerializedName("Synopsis")
    @Expose
    val Synopsis: String,
    @SerializedName("image_url")
    @Expose
    val image_url: String,
    @SerializedName("image_url_poster")
    @Expose
    val image_url_poster: String,
)