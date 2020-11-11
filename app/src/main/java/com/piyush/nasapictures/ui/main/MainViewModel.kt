package com.piyush.nasapictures.ui.main

import androidx.lifecycle.*
import com.piyush.nasapictures.Repository
import com.piyush.nasapictures.domain.LoadPhotosUseCase
import com.piyush.nasapictures.model.PhotoModel
import com.piyush.nasapictures.model.Result
import javax.inject.Inject

class MainViewModel @Inject constructor(private val loadPhotosUseCase: LoadPhotosUseCase): ViewModel() {

    init {
        loadPhotosUseCase.execute(Unit)
    }

    fun loadPhotos() = loadPhotosUseCase.observe()

    val photosResult = MediatorLiveData<List<PhotoModel>>()

    val errorMessage: LiveData<String> =
        Transformations.map(loadPhotosUseCase.observe()) {
        (it as? Result.Error)?.exception?.message ?: ""
    }

    init {
        photosResult.addSource(loadPhotos()) {
            if(it is Result.Success)
                photosResult.value = it.data
        }
    }
}