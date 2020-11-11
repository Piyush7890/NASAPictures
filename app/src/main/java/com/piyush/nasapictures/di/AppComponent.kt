package com.piyush.nasapictures.di

import com.piyush.nasapictures.ui.detail.DetailActivity
import com.piyush.nasapictures.ui.main.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataManagerModule::class, ViewModelModule::class])
interface AppComponent
{
    fun inject(activity: MainActivity)
    fun inject(loginActivity: DetailActivity)
}