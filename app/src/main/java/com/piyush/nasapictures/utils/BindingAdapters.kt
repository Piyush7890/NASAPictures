package com.piyush.nasapictures.utils

import android.graphics.Bitmap
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.piyush.nasapictures.R
import com.piyush.nasapictures.model.PhotoModel
import com.piyush.nasapictures.ui.detail.ImageLoadListener
import com.piyush.nasapictures.ui.widget.FadeInFadeOutTextView
import kotlin.random.Random


private var placeholders : Array<ColorDrawable?>? = null


@BindingAdapter(value = ["photo", "requestManager"], requireAll = true)
fun ImageView.setImage(photo: PhotoModel?, requestManager: RequestManager?)
{
    requestManager ?: return
    photo ?: return

 if(placeholders==null) {
     val placeholderColors = context.resources.getIntArray(R.array.loading_placeholders)
     placeholders = arrayOfNulls(placeholderColors.size)
     placeholderColors.indices.forEach {
         placeholders!![it] = ColorDrawable(placeholderColors[it])
     }
 }
    requestManager
        .asBitmap()
        .load(photo.imageUrl)
        .placeholder(placeholders!![Random.nextInt(placeholders!!.size)])
        .transition(BitmapTransitionOptions.withCrossFade())
        .listener(object  : RequestListener<Bitmap>
        {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Bitmap>?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }

            override fun onResourceReady(
                resource: Bitmap?,
                model: Any?,
                target: Target<Bitmap>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                if(!photo.hasFadedIn) {
                    photo.hasFadedIn = true
                    AnimationUtils.startSaturationAnimation(this@setImage, 2000)
                }
                return false
            }

        })
        .into(this)
}



@BindingAdapter("systemUiVisibilityFlags")
fun View.applySystemUiVisibilityFlags(apply : Boolean)
{
    val flags = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION )
    systemUiVisibility = flags
}


@BindingAdapter(value = ["detailImage", "requestManager", "position", "imageLoadListener"], requireAll = false)
fun ImageView.loadDetailImage(photo: String?,
                              requestManager: RequestManager?,
                              position : Int,
                              imageLoadListener : ImageLoadListener? = null)
{
    requestManager ?: return
    photo ?: return
    requestManager
        .asBitmap()
        .load(photo)
        .listener(object  : RequestListener<Bitmap>{
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Bitmap>?,
                isFirstResource: Boolean
            ): Boolean {
                imageLoadListener?.onImageLoadFailed(position, this@loadDetailImage)
                return false
            }

            override fun onResourceReady(
                resource: Bitmap?,
                model: Any?,
                target: Target<Bitmap>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                imageLoadListener?.onImageLoaded(position, this@loadDetailImage)
                return false

            }

        })
        .into(this)
}

@BindingAdapter("textToAnimate")
fun FadeInFadeOutTextView.setTextToAnimate(textToAnimate : String?)
{
    setTextToAnimate(textToAnimate)
}