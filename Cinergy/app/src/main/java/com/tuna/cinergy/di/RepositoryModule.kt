package com.tuna.cinergy.di

import com.tuna.cinergy.business.data.network.CinergyNetworkDataSource
import com.tuna.cinergy.business.repository.CineryInfoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideCinergyInfoRepository(
        cinergyNetworkDataSource: CinergyNetworkDataSource
    ): CineryInfoRepository {
        return CineryInfoRepository(cinergyNetworkDataSource)
    }
}