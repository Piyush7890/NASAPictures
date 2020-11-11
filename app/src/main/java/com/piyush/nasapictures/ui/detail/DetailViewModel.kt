package com.piyush.nasapictures.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.piyush.nasapictures.Repository
import com.piyush.nasapictures.domain.LoadPhotosUseCase
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val loadPhotosUseCase: LoadPhotosUseCase) : ViewModel() {


    fun loadPhotos()  = loadPhotosUseCase.observe()

//    private val _currentPositionLiveData = MutableLiveData<Int>()
//
//    val currentPositionLiveData : LiveData<Int>
//        get() = _currentPositionLiveData
//
//    fun setPosition(position: Int)
//    {
//        _currentPositionLiveData.value = position
//    }
}