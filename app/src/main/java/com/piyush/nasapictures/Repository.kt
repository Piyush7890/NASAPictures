package com.piyush.nasapictures

import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.piyush.nasapictures.model.PhotoModel
import java.io.InputStreamReader
import java.io.Reader
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.jvm.Throws


interface Repository
{
    fun loadPhotos() : List<PhotoModel>
}


@Singleton
class DefaultRepository @Inject constructor()
    : Repository
{


    @Throws(JsonIOException::class, JsonSyntaxException::class)
    override fun loadPhotos() : List<PhotoModel>
    {
        val dataInputStream = this.javaClass.classLoader!!
            .getResource(BuildConfig.NASA_DATA_FILENAME).openStream()
        val reader: Reader = InputStreamReader(dataInputStream)
        val photoDataType  =object :  TypeToken<ArrayList<PhotoModel>>(){}.type
        return Gson().fromJson<ArrayList<PhotoModel>>(reader, photoDataType)
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