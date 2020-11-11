package com.piyush.nasapictures.domain

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.piyush.nasapictures.BuildConfig
import com.piyush.nasapictures.Repository
import com.piyush.nasapictures.model.PhotoModel
import com.piyush.nasapictures.model.Result
import com.piyush.nasapictures.utils.AsyncScheduler
import com.piyush.nasapictures.utils.DateUtils.toDate
import com.piyush.nasapictures.utils.DefaultScheduler
import java.io.InputStreamReader
import java.io.Reader
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
open class LoadPhotosUseCase @Inject constructor(private val repository: Repository) : MediatorUseCase<Unit, List<PhotoModel>>()
{

    override fun execute(parameters: Unit) {
        result.value = Result.Loading
        DefaultScheduler.execute {
            try {
                result.postValue(Result.Success(repository.loadPhotos()))
            }catch (e : Exception)
            {
                result.postValue(Result.Error(e))
            }
        }
    }

}