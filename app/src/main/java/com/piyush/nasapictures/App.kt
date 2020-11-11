package com.piyush.nasapictures

import android.app.Application
import com.piyush.nasapictures.di.AppComponent
import com.piyush.nasapictures.di.DaggerAppComponent

class App : Application()
{

    //old school dependency injection
    var component: AppComponent? = null
    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder().build()

    }
}