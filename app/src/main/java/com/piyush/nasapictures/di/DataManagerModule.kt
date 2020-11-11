package com.piyush.nasapictures.di

import com.piyush.nasapictures.Repository
import com.piyush.nasapictures.DefaultRepository
import dagger.Binds
import dagger.Module

@Module
abstract class DataManagerModule {
    @Binds
    abstract fun provideRepository(repository: DefaultRepository): Repository
}
