package com.piyush.nasapictures

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.piyush.nasapictures.domain.LoadPhotosUseCase
import com.piyush.nasapictures.model.PhotoModel
import com.piyush.nasapictures.model.Result
import com.piyush.nasapictures.ui.main.MainViewModel
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import java.lang.Exception


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
}