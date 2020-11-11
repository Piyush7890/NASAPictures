package com.piyush.nasapictures.utils

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import androidx.core.view.marginTop

fun View.updateHeight(height : Int)
{

    if (layoutParams.height != height) {
        layoutParams.height = height
        requestLayout()
    }
}

fun View.setMargin(marginStart : Int = this.marginStart,
                   marginTop : Int = this.marginTop,
                   marginEnd : Int = this.marginEnd,
                   marginBottom : Int = this.marginBottom)
{
    val params = layoutParams as ViewGroup.MarginLayoutParams
    params.setMargins(marginStart, marginTop, marginEnd, marginBottom)
    layoutParams = params
}

fun Float.dpToPx(context: Context): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        context.resources.displayMetrics
    )
}

fun View.setGone()
{
    visibility = View.GONE
}

fun View.setVisibility(shouldBeVisible  : Boolean) = if(shouldBeVisible) setVisible() else setGone()

fun View.setVisible()
{
    visibility = View.VISIBLE
}