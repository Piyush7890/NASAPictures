package com.piyush.nasapictures.ui.detail


import androidx.lifecycle.ViewModel
import com.piyush.nasapictures.domain.LoadPhotosUseCase
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val loadPhotosUseCase: LoadPhotosUseCase)
    : ViewModel() {


    fun loadPhotos()  = loadPhotosUseCase.observe()

}
