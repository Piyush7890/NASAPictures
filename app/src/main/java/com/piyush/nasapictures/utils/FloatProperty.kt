package com.piyush.nasapictures.utils

import android.util.Property

internal abstract class FloatProperty<T>(name: String) :
    Property<T, Float>(Float::class.java, name) {

    abstract fun setValue(t: T, value: Float)

    override fun set(t: T, value: Float) {
        setValue(t, value)
    }
}