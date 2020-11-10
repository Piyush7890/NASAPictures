package com.piyush.nasapictures.domain

import androidx.lifecycle.MediatorLiveData
import com.piyush.nasapictures.model.Result

abstract class MediatorUseCase<in P, R> {
    protected val result = MediatorLiveData<Result<R>>()

    fun observe(): MediatorLiveData<Result<R>> {
        return result
    }

    abstract fun execute(parameters: P)
}