package com.piyush.nasapictures.ui.detail

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.piyush.nasapictures.databinding.DetailPhotoItemBinding
import com.piyush.nasapictures.model.PhotoModel

class DetailPhotoViewHolder(val binding: DetailPhotoItemBinding) : RecyclerView.ViewHolder(binding.root)

{
    fun bind(requestManager: RequestManager, photoModel: PhotoModel)
    {
        binding.requestManager = requestManager
        binding.photo = photoModel
        binding.position = adapterPosition
        Log.d("bind", "loadDetailImage: $adapterPosition")
        binding.executePendingBindings()
    }
}