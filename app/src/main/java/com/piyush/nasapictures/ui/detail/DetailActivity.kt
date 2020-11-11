package com.piyush.nasapictures.ui.detail

import android.app.Activity
import android.app.SharedElementCallback
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updatePadding
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.piyush.nasapictures.App
import com.piyush.nasapictures.R
import com.piyush.nasapictures.databinding.ActivityDetailBinding
import com.piyush.nasapictures.model.Result
import com.piyush.nasapictures.utils.*
import javax.inject.Inject


class DetailActivity : AppCompatActivity(), ImageLoadListener {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var isReturning = false
    private val sharedElementCallback =
        object : SharedElementCallback() {
            override fun onMapSharedElements(
                names: MutableList<String>,
                sharedElements: MutableMap<String, View>
            ) {
                if (isReturning) {

                    val view = (adapter).findViewHolderWithTag(binding.viewpager.currentItem.toString())?.binding?.image
                    if (view != null) {
                        names.clear()
                        names.add(view.transitionName)
                        sharedElements.clear()
                        sharedElements[view.transitionName] = view
                    }
                    else{
                        names.clear()
                        sharedElements.clear()
                    }
                }
            }

        }


    private lateinit var binding : ActivityDetailBinding
    private lateinit var viewModel  : DetailViewModel
    private var position : Int = 0
    private lateinit var adapter  : DetailAdapter
    private lateinit var behavior : BottomSheetBehavior<*>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_image_detail
        )
        (application as App).component!!.inject(this)
        behavior = BottomSheetBehavior.from(binding.detailsInclude.card)
        position = intent.getIntExtra(Constants.POSITION, 0)
        viewModel = ViewModelProvider(this, viewModelFactory)[DetailViewModel::class.java]
        postponeEnterTransition()
        adapter = DetailAdapter(Glide.with(this), this)
        binding.viewpager.apply {
            this.adapter = this@DetailActivity.adapter
            val pageMargin = 12f.dpToPx(this@DetailActivity)
            setPageTransformer { page, position -> page.translationX = position * pageMargin }
        }

        binding.detailsInclude.title.isSelected = true
        viewModel.loadPhotos().observe(this, Observer {
            when(it){
                is Result.Success ->
                {
                    (binding.viewpager.adapter as DetailAdapter).items = it.data
                        binding.viewpager.setCurrentItem(position, false)

                }
            }
        })
        binding.viewpager.registerOnPageChangeCallback(callback)
        setEnterSharedElementCallback(sharedElementCallback)
        binding.parent.setOnApplyWindowInsetsListener { _, windowInsets ->
            binding.bottomScrim.updateHeight(windowInsets.systemWindowInsetBottom)
            binding.detailsInclude.scrollView.updatePadding(bottom = windowInsets.systemWindowInsetBottom)
            binding.sheetParent.setMargin(marginTop = windowInsets.systemWindowInsetTop)
            windowInsets
        }
        binding.detailsInclude.title.setOnClickListener {
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
        behavior.addBottomSheetCallback(object  : BottomSheetBehavior.BottomSheetCallback()
        {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                binding.scrim.alpha = slideOffset
                binding.detailsInclude.arrowUp.rotation = 180f * slideOffset
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
            }

        })
        binding.detailsInclude.scrollView.viewTreeObserver
            .addOnScrollChangedListener {
                binding.detailsInclude.scrollView.apply {
                    binding.detailsInclude.divider.setVisibility(this.scrollY != 0)
                }
            }
    }

    private val callback = object  : ViewPager2.OnPageChangeCallback()
    {
        override fun onPageSelected(position: Int) {
            val photo  =  adapter.items[position]
            binding.detailsInclude.apply {
                this.photo = photo
                executePendingBindings()
            }

            PaletteExtensions.getColorForImage(photo.imageUrl, Glide.with(this@DetailActivity)) {
                AnimationUtils.animateCardBackgroundColor(binding.detailsInclude.card, it!!)
                AnimationUtils.animateBackgroundColor(binding.bottomScrim, it)
            }
        }
    }


    override fun onDestroy() {
        binding.viewpager.unregisterOnPageChangeCallback(callback)
        super.onDestroy()
    }

    override fun onImageLoaded(photoModel: Int, view: View) {
        if(photoModel == position) {
            startPostponedEnterTransition()
        }
    }

    override fun onImageLoadFailed(photoModel: Int, view: View) {
        onImageLoaded(photoModel, view)
    }


    override fun finishAfterTransition() {
        setResult()
        super.finishAfterTransition()
    }

    private fun setResult() {
        val position: Int = binding.viewpager.currentItem
        val data = Intent()
        data.putExtra(Constants.VIEWPAGER_POSITION, position)
        setResult(Activity.RESULT_OK, data)
    }

    override fun onBackPressed() {
        if(behavior.state == BottomSheetBehavior.STATE_EXPANDED) {
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        else {
            isReturning = true
            super.onBackPressed()
        }
    }

    companion object
    {
        fun getViewPagerPosition(resultCode : Int, data : Intent?)  : Int
        {
            if(resultCode== Activity.RESULT_OK && data!=null)
                return data.getIntExtra(Constants.VIEWPAGER_POSITION, RecyclerView.NO_POSITION)
            return  RecyclerView.NO_POSITION
        }
    }

}