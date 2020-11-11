package com.piyush.nasapictures.ui.main

import android.app.ActivityOptions
import android.app.SharedElementCallback
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updatePadding
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.piyush.nasapictures.App
import com.piyush.nasapictures.R
import com.piyush.nasapictures.databinding.ActivityMainBinding
import com.piyush.nasapictures.model.Result
import com.piyush.nasapictures.ui.detail.DetailActivity
import com.piyush.nasapictures.utils.AnimationUtils.fadeIn
import com.piyush.nasapictures.utils.AnimationUtils.fadeOut
import com.piyush.nasapictures.utils.Constants
import com.piyush.nasapictures.utils.updateHeight
import javax.inject.Inject


class MainActivity : AppCompatActivity(), GridImageClickHandler
{
    private lateinit var binding : ActivityMainBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel  : MainViewModel
    private lateinit var adapter  : PhotosAdapter
    private var sharedElementPosition = RecyclerView.NO_POSITION
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )
        window.exitTransition = null
        (application as App).component!!.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        adapter = PhotosAdapter(Glide.with(this), this)
        binding.list.adapter = adapter
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
                    binding.list.fadeIn()
                    (adapter).items = it.data
                }

                is Result.Error ->
                {
                    binding.progress.fadeOut()
                    Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
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
                    if (sharedElementPosition!=RecyclerView.NO_POSITION && sharedElementPosition<adapter.items.size) {
                        // Locate the ViewHolder for the clicked position.
                        val selectedViewHolder = binding.list.findViewHolderForAdapterPosition(sharedElementPosition) as PhotoViewHolder?
                            ?: return
                        val transitionName = selectedViewHolder.binding.image.transitionName
                        names.clear()
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
            postponeEnterTransition()
            binding.list.addOnLayoutChangeListener(object  : View.OnLayoutChangeListener
            {
                override fun onLayoutChange(
                    p0: View?, p1: Int, p2: Int, p3: Int,
                    p4: Int, p5: Int, p6: Int, p7: Int, p8: Int) {
                    binding.list.removeOnLayoutChangeListener(this)
                    startPostponedEnterTransition()
                }
            })
            binding.list.scrollToPosition(sharedElementPosition)
        }
        }

 }



