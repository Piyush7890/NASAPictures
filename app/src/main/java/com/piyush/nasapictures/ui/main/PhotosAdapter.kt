package com.piyush.nasapictures.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.piyush.nasapictures.databinding.PhotoItemBinding
import com.piyush.nasapictures.model.PhotoModel
import com.piyush.nasapictures.ui.BaseAdapter

class PhotosAdapter(private val requestManager: RequestManager,
                    private val actionHandler: GridImageClickHandler)
    : BaseAdapter<PhotoModel, PhotoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return    PhotoViewHolder(PhotoItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false))
            .apply {
                binding.image.setOnClickListener {
                    actionHandler.onGridImageClick(adapterPosition, it)
                }
            }
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(requestManager, items[position])
    }
}