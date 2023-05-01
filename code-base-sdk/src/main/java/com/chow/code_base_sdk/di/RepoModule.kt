package com.chow.code_base_sdk.di

import com.chow.code_base_sdk.rest.BaseRepository
import com.chow.code_base_sdk.rest.BaseRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepoModule {

    @Binds
    abstract fun providesRepo(
        repositoryImpl: BaseRepositoryImpl
    ): BaseRepository
}