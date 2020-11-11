package com.piyush.nasapictures.ui.main

import android.annotation.TargetApi
import android.app.ActivityOptions
import android.app.SharedElementCallback
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.transition.Transition
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updatePadding
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.piyush.nasapictures.R
import com.piyush.nasapictures.databinding.ActivityMainBinding
import com.piyush.nasapictures.model.Result
import com.piyush.nasapictures.ui.detail.DetailActivity
import com.piyush.nasapictures.utils.AnimationUtils.fadeIn
import com.piyush.nasapictures.utils.AnimationUtils.fadeOut
import com.piyush.nasapictures.utils.Constants
import com.piyush.nasapictures.utils.updateHeight


class MainActivity : AppCompatActivity(), GridImageClickHandler
{
    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel  : MainViewModel
    private var sharedElementPosition = RecyclerView.NO_POSITION
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

        binding.parent.setOnApplyWindowInsetsListener { _, windowInsets ->
            binding.statusBarScrim.updateHeight(windowInsets.systemWindowInsetTop)
            binding.list.updatePadding(bottom = windowInsets.systemWindowInsetBottom)
            windowInsets
        }


        setExitSharedElementCallback(
            object : SharedElementCallback() {
                override fun onMapSharedElements(
                    names: MutableList<String?>,
                    sharedElements: MutableMap<String?, View?>
                ) {
                    Toast.makeText(this@MainActivity, sharedElements.isEmpty().toString(), Toast.LENGTH_SHORT).show()
                    if (sharedElementPosition!=RecyclerView.NO_POSITION) {
                        // Locate the ViewHolder for the clicked position.
                        val selectedViewHolder = binding.list.findViewHolderForAdapterPosition(sharedElementPosition) as PhotoViewHolder?
                            ?: return
                        val transitionName = selectedViewHolder.binding.image.transitionName
                        names.clear()
                        Toast.makeText(this@MainActivity, transitionName, Toast.LENGTH_SHORT).show()
                        names.add(transitionName)
                        // Map the first shared element name to the child ImageView.
                        sharedElements.clear()
                        sharedElements[transitionName] = selectedViewHolder.binding.image
                        sharedElementPosition = RecyclerView.NO_POSITION
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

    override fun onActivityReenter(resultCode: Int, data: Intent?) {
        sharedElementPosition = DetailActivity.getViewPagerPosition(resultCode, data)
        if (sharedElementPosition != RecyclerView.NO_POSITION) {
            binding.list.scrollToPosition(sharedElementPosition)
        }


    }


}
