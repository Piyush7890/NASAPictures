package com.piyush.nasapictures.ui.detail

import androidx.lifecycle.ViewModel
import com.piyush.nasapictures.DataManager
import com.piyush.nasapictures.RealDataManager
import com.piyush.nasapictures.domain.LoadPhotosUseCase

class DetailViewModel() : ViewModel() {

    private val dataManager: DataManager = RealDataManager(LoadPhotosUseCase())
    fun loadPhotos() = dataManager.loadPhotos()

}