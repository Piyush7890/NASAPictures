package com.piyush.nasapictures.di

import com.piyush.nasapictures.DataManager
import com.piyush.nasapictures.RealDataManager
import dagger.Binds
import dagger.Module

@Module
abstract class DataManagerModule {
    @Binds
    abstract fun provideRepository(repository: RealDataManager): DataManager
}
