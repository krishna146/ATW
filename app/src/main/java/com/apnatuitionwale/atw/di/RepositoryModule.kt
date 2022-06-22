package com.apnatuitionwale.atw.di

import com.apnatuitionwale.atw.repository.AuthRepository
import com.apnatuitionwale.atw.repository.AuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindsAuthRepositoryImpl(authRepositoryImpl: AuthRepositoryImpl): AuthRepository
}