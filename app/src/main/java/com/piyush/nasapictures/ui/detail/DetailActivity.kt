package com.piyush.nasapictures.ui.detail

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.transition.ArcMotion
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnPreDraw
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialContainerTransformSharedElementCallback
import com.piyush.nasapictures.R
import com.piyush.nasapictures.databinding.ActivityDetailBinding
import com.piyush.nasapictures.model.Result
import com.piyush.nasapictures.utils.AnimationUtils
import com.piyush.nasapictures.utils.Constants

class DetailActivity : AppCompatActivity(), ImageLoadListener {

    private lateinit var binding : ActivityDetailBinding
    private lateinit var viewModel  : DetailViewModel
    private var position : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_image_detail
        )
        position = intent.getIntExtra(Constants.POSITION, 0)
        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        postponeEnterTransition()
        binding.viewpager.adapter = DetailAdapter(Glide.with(this), this)

        viewModel.loadPhotos().observe(this, Observer {
            when(it){
                is Result.Success ->
                {
                    (binding.viewpager.adapter as DetailAdapter).items = it.data
                        binding.viewpager.setCurrentItem(position, false)

                }
            }
        })
    }

    override fun onImageLoaded(photoModel: Int, view: View) {
        if(photoModel == position) {
//            setUpTransition(view)
            startPostponedEnterTransition()
        }
    }

    override fun onImageLoadFailed(photoModel: Int, view: View) {
        onImageLoaded(photoModel, view)
    }


    fun setUpTransition(view : View)
    {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())
            window.sharedElementEnterTransition = MaterialContainerTransform()
                .apply {
                    addTarget(view)
                    duration = 250L
                    scrimColor = Color.TRANSPARENT
                    interpolator = AnimationUtils.fastOutSlowInInterpolator
                    pathMotion = ArcMotion().apply { maximumAngle = 90f }
                }

            window.sharedElementReturnTransition = MaterialContainerTransform()
                .apply {
                    addTarget(view)
                    duration = 225L
                    scrimColor = Color.TRANSPARENT
                    interpolator = AnimationUtils.fastOutSlowInInterpolator
                    pathMotion = ArcMotion().apply {
                        minimumVerticalAngle =55f
                        minimumHorizontalAngle=35f
                        maximumAngle=45f
                    }
                }
        }
    }

}