package com.piyush.nasapictures.model

import com.google.gson.annotations.SerializedName


data class PhotoModel (

    val copyright : String,

    val date : String,

    val explanation : String,

    @SerializedName("hdurl")
    val hdImageUrl : String,

    @SerializedName("media_type")
    val mediaType : String,

    @SerializedName("service_version")
    val serviceVersion : String,

    val title : String,

    @SerializedName("url")
    val imageUrl : String
)