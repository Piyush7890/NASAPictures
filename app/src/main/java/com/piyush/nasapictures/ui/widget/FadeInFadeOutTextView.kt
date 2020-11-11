package com.piyush.nasapictures.ui.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import com.piyush.nasapictures.utils.AnimationUtils

class FadeInFadeOutTextView : androidx.appcompat.widget.AppCompatTextView {
    private var mSubtitleAnimator =
        ObjectAnimator.ofFloat(this, View.ALPHA, 1f, 0f, 1f).apply {
            duration = 2L * /*getLongAnimTime(this@FadeInFadeOutTextView.resources)*/200
            interpolator = AnimationUtils.fastOutSlowInInterpolator

        }

    private var mNextSubtitle: CharSequence? = null

    constructor(context: Context) : this(context,null)

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : this(context, attrs,0)

    constructor(
        context: Context, attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        val listener = AnimatorListener()
        mSubtitleAnimator.addUpdateListener(listener)
        mSubtitleAnimator.addListener(listener)
    }


    fun setTextToAnimate(subtitle : String?)
    {
        if (TextUtils.equals(text, subtitle)) {
            return
        }
        mNextSubtitle = subtitle
        if (!mSubtitleAnimator.isRunning) {
            mSubtitleAnimator.start()
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
            if (mNextSubtitle != null) {
                mTextUpdated = false
                animator.start()
            }
        }

        private fun ensureTextUpdated() {
            if (!mTextUpdated) {
                if (mNextSubtitle != null) {
                    super@FadeInFadeOutTextView.setText(mNextSubtitle)
                    mNextSubtitle = null
                }
                mTextUpdated = true
            }
        }
    }
}
