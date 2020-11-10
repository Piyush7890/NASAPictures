package com.piyush.nasapictures.utils

import android.graphics.ColorMatrix
import android.util.Property


 // A ColorMatrix extension which caches the saturation value for animation purposes.

class ObservableColorMatrix : ColorMatrix() {
    private var saturation = 1f

    fun getSaturation(): Float {
        return saturation
    }

    override fun setSaturation(saturation: Float) {
        this.saturation = saturation
        super.setSaturation(saturation)
    }

    companion object {
        val SATURATION: Property<ObservableColorMatrix, Float> =

                object : FloatProperty<ObservableColorMatrix>("saturation") {

                    override fun get(matrix: ObservableColorMatrix): Float {
                        return matrix.getSaturation()
                    }

                    override fun setValue(matrix: ObservableColorMatrix, value: Float) {
                        matrix.setSaturation(value)
                    }
                }
    }
}
