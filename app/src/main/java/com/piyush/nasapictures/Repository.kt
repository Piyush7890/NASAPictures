package com.piyush.nasapictures

import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.piyush.nasapictures.domain.LoadPhotosUseCase
import com.piyush.nasapictures.model.PhotoModel
import com.piyush.nasapictures.model.Result
import com.piyush.nasapictures.utils.DateUtils.toDate
import java.io.InputStreamReader
import java.io.Reader
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton


interface Repository
{
    fun loadPhotos() : List<PhotoModel>
}


@Singleton
class DefaultRepository @Inject constructor()
    : Repository
{


    override fun loadPhotos() : List<PhotoModel>
    {

            val dataInputStream = this.javaClass.classLoader!!
                .getResource(BuildConfig.NASA_DATA_FILENAME).openStream()
            val reader: Reader = InputStreamReader(dataInputStream)
            val photoDataType  =object :  TypeToken<ArrayList<PhotoModel>>(){}.type
            val data = Gson().fromJson<ArrayList<PhotoModel>>(reader, photoDataType)
            //we want to show the latest photos first

         return data
    }

}


class FakeRepository : Repository{
    companion object
    {
        val TEST_DATA = listOf(
            PhotoModel("a","a","a","a","a", "a", "a", "a"),
            PhotoModel("b","b","b","b","b", "b", "b", "b"),
            PhotoModel("c","c","c","c","c", "c", "c", "c")
        )
    }
    override fun loadPhotos(): List<PhotoModel> {
        return TEST_DATA
    }

}