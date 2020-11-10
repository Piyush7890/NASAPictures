package com.piyush.nasapictures

import androidx.lifecycle.ViewModel

class MainViewModel(private val dataManager: DataManager) : ViewModel() {


    fun loadPhotos() = dataManager.loadPhotos()

}