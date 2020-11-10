package com.piyush.nasapictures.domain

import com.piyush.nasapictures.model.PhotoModel
import com.piyush.nasapictures.model.Result
import com.piyush.nasapictures.utils.AsyncScheduler
import com.piyush.nasapictures.utils.DateUtils.toDate
import com.piyush.nasapictures.utils.JsonParser
import java.lang.Exception


class LoadPhotosUseCase : MediatorUseCase<Unit, List<PhotoModel>>()
{

    override fun execute(parameters: Unit) {
        result.value = Result.Loading
        AsyncScheduler.execute {
            try {
                //we want to show latest photos first
                val data = JsonParser.parseData().sortedByDescending { it.date.toDate() }
                result.postValue(Result.Success(data))
            }catch (e : Exception)
            {
                result.postValue(Result.Error(e))
            }
        }
    }

}