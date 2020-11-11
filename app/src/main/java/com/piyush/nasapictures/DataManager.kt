package com.piyush.nasapictures

import androidx.lifecycle.LiveData
import com.piyush.nasapictures.domain.LoadPhotosUseCase
import com.piyush.nasapictures.model.PhotoModel
import com.piyush.nasapictures.model.Result
import javax.inject.Inject
import javax.inject.Singleton


// interface incase we want to create a mock of Data manager
interface DataManager
{
    fun loadPhotos() : LiveData<Result<List<PhotoModel>>>
}


//I have created this class in case if we want to save the data to the database first
@Singleton
class RealDataManager @Inject constructor(private val loadPhotosUseCase: LoadPhotosUseCase)
    : DataManager
{

    // by lazy because we want to execute this only for the first time
    private val loadPhotosLiveData by lazy {
        loadPhotosUseCase.execute(Unit)
        return@lazy loadPhotosUseCase.observe()
    }


    override fun loadPhotos(): LiveData<Result<List<PhotoModel>>> {
        return loadPhotosLiveData
    }

}