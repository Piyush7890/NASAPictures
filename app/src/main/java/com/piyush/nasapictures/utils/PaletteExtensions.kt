package com.piyush.nasapictures.utils

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.animation.Interpolator
import androidx.core.math.MathUtils
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.card.MaterialCardView
import dev.jorgecastillo.androidcolorx.library.shades
import java.util.concurrent.ConcurrentHashMap

object PaletteExtensions
{

    private val colorCache  = ConcurrentHashMap<String, Int>()

    private fun generatePalette(bitmap: Bitmap,
                                left : Int = 0,
                                top : Int = 0,
                                right : Int = bitmap.width,
                                bottom : Int = bitmap.height ): Palette {
        return Palette.from(bitmap).clearFilters()
            .setRegion(left, top, right, bottom)
            .addFilter(PaletteFilter.INSTANCE)
            .generate()
    }


    fun getColorForImage(url : String, requestManager: RequestManager,  runAfterColorFound: (Int?) -> Unit)
    {
        if(colorCache.containsKey(url))
            runAfterColorFound(colorCache[url]!!)
        else
            requestManager.asBitmap().load(url).into(object  : CustomTarget<Bitmap>(){

                override fun onLoadCleared(placeholder: Drawable?) {
                }

                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    val color = vibrantFirst(resource)
                    colorCache[url] = color
                    runAfterColorFound(color)
                }

            })
    }

    fun vibrantFirst(bitmap: Bitmap): Int {

        val palette = generatePalette(bitmap)
        var emptyList = emptyList<Int>()
        var backgroundColor = Color.parseColor("#030303")
        getVibrantFirstSwatch(palette)?.let {
            emptyList =  it.rgb.shades()
        }
        if (emptyList.isNotEmpty()) {
            backgroundColor = emptyList[MathUtils.clamp(emptyList.size - 5, 0, emptyList.size)]
        }

        return backgroundColor
    }


    private fun getVibrantFirstSwatch(palette: Palette) : Palette.Swatch?
    {
        return when {
            palette.vibrantSwatch != null -> {
                palette.vibrantSwatch!!
            }
            palette.mutedSwatch != null -> {
                palette.mutedSwatch!!
            }
            palette.darkVibrantSwatch != null -> {

                palette.darkVibrantSwatch!!
            }
            palette.lightVibrantSwatch != null -> {
                palette.lightVibrantSwatch!!
            }
            else -> null
        }
    }

    fun isBlack(hsl: FloatArray) = hsl[2] <= 0.08f

    fun isNearRedLine(hsl: FloatArray): Boolean {
        return hsl[0] in 10.0f..37.0f && hsl[1] <= 0.62f
    }


    internal class PaletteFilter : Palette.Filter {

        override fun isAllowed(rgb: Int, hsl: FloatArray): Boolean {
            return !(!isBlack(hsl) and isNearRedLine(
                hsl
            ))
        }

        companion object {
            val INSTANCE = PaletteFilter()
        }
    }


}