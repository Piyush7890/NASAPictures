package com.piyush.nasapictures.domain

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.piyush.nasapictures.BuildConfig
import com.piyush.nasapictures.model.PhotoModel
import com.piyush.nasapictures.model.Result
import com.piyush.nasapictures.utils.AsyncScheduler
import com.piyush.nasapictures.utils.DateUtils.toDate
import java.io.InputStreamReader
import java.io.Reader
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LoadPhotosUseCase @Inject constructor() : MediatorUseCase<Unit, List<PhotoModel>>()
{

    override fun execute(parameters: Unit) {
        result.value = Result.Loading
        AsyncScheduler.execute {
            try {
                val dataInputStream = this.javaClass.classLoader!!
                    .getResource(BuildConfig.NASA_DATA_FILENAME).openStream()
                val reader: Reader = InputStreamReader(dataInputStream)
                val photoDataType  =object :  TypeToken<ArrayList<PhotoModel>>(){}.type
                val data = Gson().fromJson<ArrayList<PhotoModel>>(reader, photoDataType)
                //we want to show the latest photos first
                data.sortedByDescending { it.date.toDate() }
                result.postValue(Result.Success(data))
            }catch (e : Exception)
            {
                result.postValue(Result.Error(e))
            }
        }
    }

}