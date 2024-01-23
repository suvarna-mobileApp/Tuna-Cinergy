package com.tuna.cinergy.di

import android.content.Context
import com.google.gson.Gson
import com.tuna.cinergy.BuildConfig
import com.tuna.cinergy.business.data.network.CinergyNetworkDataSource
import com.tuna.cinergy.business.data.network.CinergyNetworkDataSourceImpl
import com.tuna.cinergy.datasource.network.RetrofitService
import com.tuna.cinergy.datasource.network.RetrofitServiceImpl
import com.tuna.cinergy.datasource.network.retrofit.WebServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @AppRetrofit
    @Singleton
    @Provides
    fun provideRetrofitBuilder(
        gson: Gson,
        @ApplicationContext context: Context
    ): Retrofit.Builder{

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)

        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .addInterceptor(
                com.tuna.cinergy.business.domain.utils.networkException.ConnectivityInterceptor(
                    context
                )
            )
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun provideRetrofit(@AppRetrofit retrofit: Retrofit.Builder): Retrofit {
        return retrofit.build()
    }

    @Singleton
    @Provides
    fun provideErrorUtils(retrofit: Retrofit): com.tuna.cinergy.business.domain.state.ErrorUtils {
        return com.tuna.cinergy.business.domain.state.ErrorUtils(retrofit)
    }

    @Singleton
    @Provides
    fun provideCinergyNetworkDataSource(
        retrofitService: RetrofitService,
    ): CinergyNetworkDataSource {
        return CinergyNetworkDataSourceImpl(
            retrofitService
        )
    }

    @Singleton
    @Provides
    fun provideCinergyService(@AppRetrofit retrofit: Retrofit.Builder): WebServices {
        return retrofit
            .build()
            .create(WebServices::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofitService(
        webServices: WebServices
    ): RetrofitService {
        return RetrofitServiceImpl(webServices)
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AppRetrofit