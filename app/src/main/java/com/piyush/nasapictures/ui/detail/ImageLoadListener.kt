package com.piyush.nasapictures.ui.detail

import android.view.View
import com.piyush.nasapictures.model.PhotoModel

interface ImageLoadListener
{
    fun onImageLoaded(photoModel: Int, view : View)
    fun onImageLoadFailed(photoModel: Int,  view : View)
}