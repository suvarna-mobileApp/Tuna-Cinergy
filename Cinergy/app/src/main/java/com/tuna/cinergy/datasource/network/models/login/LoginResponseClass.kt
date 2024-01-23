package com.tuna.cinergy.datasource.network.models.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginResponseClass (
        @SerializedName("response")
        @Expose
        val response: String,
        @SerializedName("token")
        @Expose
        val token: String?,
        @SerializedName("printer_name")
        @Expose
        val printer_name: String?,
        @SerializedName("food_menu")
        @Expose
        val food_menu: String?,
        @SerializedName("attractions_menu")
        @Expose
        val attractions_menu: String?,
        @SerializedName("id")
        @Expose
        val id: Int,
        @SerializedName("location")
        @Expose
        val location: String?,
        )