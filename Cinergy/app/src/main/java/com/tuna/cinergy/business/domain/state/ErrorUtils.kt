package com.tuna.cinergy.business.domain.state

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException


class ErrorUtils constructor(
    private val retrofit: Retrofit
) {

    fun parseError(response: Response<*>): com.tuna.cinergy.business.domain.state.APIError? {
        val converter: Converter<ResponseBody, com.tuna.cinergy.business.domain.state.APIError> = retrofit
            .responseBodyConverter(com.tuna.cinergy.business.domain.state.APIError::class.java, arrayOfNulls<Annotation>(0))
        val error: com.tuna.cinergy.business.domain.state.APIError = try {
            converter.convert(response.errorBody())!!
        } catch (e: IOException) {
            return com.tuna.cinergy.business.domain.state.APIError()
        }
        return error
    }
}