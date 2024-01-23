package com.tuna.cinergy.datasource.network.models.guestlogin

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GuestLoginResponseClass (
    @SerializedName("response")
    @Expose
    val response: String,
    @SerializedName("user")
    @Expose
    val user: UserData,
        )


data class UserData (
    @SerializedName("id")
    @Expose
    val id: String?,

    @SerializedName("member_id")
    @Expose
    val member_id: String?,
)