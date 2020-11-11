package com.piyush.nasapictures.utils

import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.piyush.nasapictures.BuildConfig
import com.piyush.nasapictures.model.PhotoModel
import java.io.InputStreamReader
import java.io.Reader
import kotlin.jvm.Throws


object JsonParser
{

  @Throws(JsonIOException::class, JsonSyntaxException::class)
  fun parseData() : List<PhotoModel>
  {
    val dataInputStream = this.javaClass.classLoader!!
      .getResource(BuildConfig.NASA_DATA_FILENAME).openStream()
    val reader: Reader = InputStreamReader(dataInputStream)
    val photoDataType  =object :  TypeToken<ArrayList<PhotoModel>>(){}.type
    return Gson().fromJson<ArrayList<PhotoModel>>(reader, photoDataType)
  }



}