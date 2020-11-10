package com.piyush.nasapictures.ui.main

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.piyush.nasapictures.databinding.PhotoItemBinding
import com.piyush.nasapictures.model.PhotoModel

class PhotoViewHolder(private val binding : PhotoItemBinding) : RecyclerView.ViewHolder(binding.root)
{
    fun bind(requestManager: RequestManager, photoModel: PhotoModel)
    {
        binding.requestManager = requestManager
        binding.photo = photoModel
        binding.executePendingBindings()
    }
}