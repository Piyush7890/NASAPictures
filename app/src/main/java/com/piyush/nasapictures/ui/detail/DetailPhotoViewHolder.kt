package com.piyush.nasapictures.ui.detail

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.piyush.nasapictures.databinding.DetailPhotoItemBinding
import com.piyush.nasapictures.model.PhotoModel

class DetailPhotoViewHolder(val binding: DetailPhotoItemBinding) : RecyclerView.ViewHolder(binding.root)

{
    fun bind(requestManager: RequestManager, photoModel: PhotoModel)
    {
        binding.apply {
            this.requestManager = requestManager
            photo = photoModel
            position = adapterPosition
            executePendingBindings()
        }
    }
}