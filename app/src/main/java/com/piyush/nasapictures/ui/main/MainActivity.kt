package com.piyush.nasapictures.ui.main

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.piyush.nasapictures.R
import com.piyush.nasapictures.databinding.ActivityMainBinding
import com.piyush.nasapictures.model.PhotoModel
import com.piyush.nasapictures.model.Result
import com.piyush.nasapictures.ui.detail.DetailActivity
import com.piyush.nasapictures.utils.AnimationUtils.fadeIn
import com.piyush.nasapictures.utils.AnimationUtils.fadeOut
import com.piyush.nasapictures.utils.Constants

class MainActivity : AppCompatActivity(), GridImageClickHandler
{
    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel  : MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding.list.adapter = PhotosAdapter(Glide.with(this), this)
        viewModel.loadPhotos().observe(this, Observer {
            when(it)
            {
                is Result.Loading ->
                {
                    binding.progress.fadeIn()
                }

                is Result.Success ->
                {
                    binding.progress.fadeOut()
                    (binding.list.adapter as PhotosAdapter).items = it.data
                }
            }
        })

    }

    override fun onGridImageClick(photo: Int, view : View) {
        val bundle = ActivityOptions.makeSceneTransitionAnimation(this,
            view,view.transitionName).toBundle()
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(Constants.POSITION, photo)
        startActivity(intent,bundle)
    }
}
