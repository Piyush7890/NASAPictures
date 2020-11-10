package com.piyush.nasapictures.utils

import android.R
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.ColorMatrixColorFilter
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.core.view.animation.PathInterpolatorCompat
import androidx.interpolator.view.animation.FastOutSlowInInterpolator


object AnimationUtils{

    val fastOutSlowInInterpolator by lazy {
        PathInterpolatorCompat.create(0.4f, 0.0f, 0.2f, 1.0f)
    }

    fun startSaturationAnimation(
        target: ImageView,
        duration: Long
    ) {
        target.setHasTransientState(true)
        val matrix= ObservableColorMatrix()
        val saturation = ObjectAnimator.ofFloat(
            matrix, ObservableColorMatrix.SATURATION, 0f, 1f
        )
        saturation.addUpdateListener {
            target.colorFilter = ColorMatrixColorFilter(matrix)
        }
        saturation.duration = duration
        saturation.interpolator = fastOutSlowInInterpolator
        saturation.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                target.clearColorFilter()
                target.setHasTransientState(false)
            }
        })
        saturation.start()
    }


    fun View.fadeIn(duration: Long=200, delay: Long = 0) {
        if (visibility == View.VISIBLE) {
            // Cancel any starting animation.
            alpha = 1f
            return
        }
        alpha = 0f
        visibility = View.VISIBLE
        animate()
            .alpha(1f)
            .setDuration(duration)
            .setStartDelay(delay)
            .setInterpolator(FastOutSlowInInterpolator()) // NOTE: We need to remove any previously set listener or Android will reuse it.
            .setListener(null)
            .start()
    }

    fun View.fadeOut(duration: Long =200, visibilityMode: Int = View.GONE, delay : Long = 0)
    {
        clearAnimation()
        if(visibility==visibilityMode)
            return
        animate()
            .alpha(0f)
            .setDuration(duration)
            .setStartDelay(delay)
            .setInterpolator(fastOutSlowInInterpolator)
            .setListener(object  :
                SimpleAnimationListener()
            {
                override fun onAnimationEnd(animation: Animator?) {
                    visibility = visibilityMode
                }
            }).start()
    }


}