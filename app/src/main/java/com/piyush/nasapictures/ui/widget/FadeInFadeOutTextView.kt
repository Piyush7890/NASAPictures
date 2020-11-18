package com.piyush.nasapictures.ui.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.piyush.nasapictures.utils.AnimationUtils

class FadeInFadeOutTextView  @JvmOverloads constructor(context: Context,
                                                       attrs: AttributeSet?=null,
                                                       defStyleAttr: Int = 0)
    : AppCompatTextView(context, attrs, defStyleAttr) {

    private var textFadeAnimator =
        ObjectAnimator.ofFloat(this, View.ALPHA, 1f, 0f, 1f).apply {
            duration = 2L * /*getLongAnimTime(this@FadeInFadeOutTextView.resources)*/200
            interpolator = AnimationUtils.fastOutSlowInInterpolator

        }

    private var nextText: CharSequence? = null

    init {
        val listener = AnimatorListener()
        textFadeAnimator.addUpdateListener(listener)
        textFadeAnimator.addListener(listener)
    }


    fun setTextToAnimate(textToAnimate : String?)
    {
        if (TextUtils.equals(text, textToAnimate)) {
            return
        }
        nextText = textToAnimate
        if (!textFadeAnimator.isRunning) {
            textFadeAnimator.start()
        }
    }



    private inner class AnimatorListener : AnimatorListenerAdapter(),
        ValueAnimator.AnimatorUpdateListener {
        private var mTextUpdated = false
        override fun onAnimationUpdate(animator: ValueAnimator) {
            if (animator.animatedFraction < 0.5) {
                mTextUpdated = false
            } else {
                ensureTextUpdated()
            }
        }

        override fun onAnimationEnd(animator: Animator) {
            ensureTextUpdated()
            if (nextText != null) {
                mTextUpdated = false
                animator.start()
            }
        }

        private fun ensureTextUpdated() {
            if (!mTextUpdated) {
                if (nextText != null) {
                    super@FadeInFadeOutTextView.setText(nextText)
                    nextText = null
                }
                mTextUpdated = true
            }
        }
    }
}
