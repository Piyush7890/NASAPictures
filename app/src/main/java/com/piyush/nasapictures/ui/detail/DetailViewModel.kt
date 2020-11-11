package com.piyush.nasapictures.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.piyush.nasapictures.DataManager
import com.piyush.nasapictures.RealDataManager
import com.piyush.nasapictures.domain.LoadPhotosUseCase
import java.text.FieldPosition
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val dataManager: DataManager) : ViewModel() {

    fun loadPhotos() = dataManager.loadPhotos()

    private val _currentPositionLiveData = MutableLiveData<Int>()

    val currentPositionLiveData : LiveData<Int>
        get() = _currentPositionLiveData

    fun setPosition(position: Int)
    {
        _currentPositionLiveData.value = position
    }
}