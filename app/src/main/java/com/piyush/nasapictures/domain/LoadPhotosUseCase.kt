package com.piyush.nasapictures.domain


import com.piyush.nasapictures.Repository
import com.piyush.nasapictures.model.PhotoModel
import com.piyush.nasapictures.model.Result
import com.piyush.nasapictures.utils.DateUtils.toDate
import com.piyush.nasapictures.utils.DefaultScheduler
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
open class LoadPhotosUseCase @Inject constructor(private val repository: Repository) : MediatorUseCase<Unit, List<PhotoModel>>()
{

    override fun execute(parameters: Unit) {
        result.value = Result.Loading
        DefaultScheduler.execute {
            try {
                //latest image first
                val data = repository.loadPhotos().sortedByDescending { it.date.toDate() }
                result.postValue(Result.Success(data))
            }catch (e : Exception)
            {
                result.postValue(Result.Error(e))
            }
        }
    }

}