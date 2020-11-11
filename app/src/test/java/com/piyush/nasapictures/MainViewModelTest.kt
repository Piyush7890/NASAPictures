package com.piyush.nasapictures

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.piyush.nasapictures.domain.LoadPhotosUseCase
import com.piyush.nasapictures.model.PhotoModel
import com.piyush.nasapictures.model.Result
import com.piyush.nasapictures.ui.main.MainViewModel
import com.piyush.nasapictures.utils.DateUtils.toDate
import org.hamcrest.*
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test


class MainViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var syncTaskExecutorRule = SyncTaskExecutorRule()


    object PhotosLoadFailUseCase : LoadPhotosUseCase(FakeRepository())
    {
        override fun execute(parameters: Unit) {
            result.value = Result.Error(Exception("Error!"))
        }
    }


    @Test
    fun testPhotosLoaded() {
        val viewModel = MainViewModel(
            LoadPhotosUseCase(FakeRepository())
        )
        viewModel.photosResult.observeOnce<List<PhotoModel>> {
            MatcherAssert.assertThat(FakeRepository.TEST_DATA, Matchers.equalTo(it))
        }
    }

    @Test
    fun testPhotosLoaded_failed()
    {
        val viewModel = MainViewModel(
            PhotosLoadFailUseCase
        )
        viewModel.errorMessage.observeOnce {
            assertTrue(it.isNotEmpty())
        }
    }

    @Test
    fun testPhotosLoaded_latestFirst()
    {
        val viewModel = MainViewModel(
            LoadPhotosUseCase(DefaultRepository())
        )
        viewModel.photosResult.observeOnce<List<PhotoModel>> {
            MatcherAssert.assertThat(it, IsLatestImageFirstMatcher)
        }
    }



    object IsLatestImageFirstMatcher  : TypeSafeMatcher<List<PhotoModel>>() {

            override fun describeTo(description: Description) {
                description.appendText("describe the error has you like more")
            }

            override fun matchesSafely(item: List<PhotoModel>): Boolean {

                for (i in 0 until item.size - 1) {
                     if(!compareDates(item[i].date , item[i + 1].date)) return false
                }
                return true
            }


        private fun compareDates(dateString1: String, dateString2: String) : Boolean {

            val date1 = dateString1.toDate()
            val date2 = dateString2.toDate()
            return date1?.after(date2)==true || date1 == date2
        }
    }

}