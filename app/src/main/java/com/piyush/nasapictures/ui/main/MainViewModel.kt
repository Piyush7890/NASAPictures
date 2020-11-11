package com.piyush.nasapictures.ui.main

import androidx.lifecycle.ViewModel
import com.piyush.nasapictures.DataManager
import com.piyush.nasapictures.RealDataManager
import com.piyush.nasapictures.domain.LoadPhotosUseCase
import javax.inject.Inject

class MainViewModel @Inject constructor(private val dataManager: DataManager): ViewModel() {

    fun loadPhotos() = dataManager.loadPhotos()

}