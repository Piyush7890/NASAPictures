package com.piyush.nasapictures.ui.main

import androidx.lifecycle.ViewModel
import com.piyush.nasapictures.DataManager
import com.piyush.nasapictures.RealDataManager
import com.piyush.nasapictures.domain.LoadPhotosUseCase

class MainViewModel() : ViewModel() {

    private val dataManager: DataManager = RealDataManager(LoadPhotosUseCase())
    fun loadPhotos() = dataManager.loadPhotos()

}