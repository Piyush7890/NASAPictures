package com.piyush.nasapictures.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.piyush.nasapictures.ui.detail.DetailViewModel
import com.piyush.nasapictures.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap



@Module
internal abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun provideMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    abstract fun provideLoginViewModel(detailViewModel: DetailViewModel): ViewModel

    @Binds
    abstract fun provideFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}