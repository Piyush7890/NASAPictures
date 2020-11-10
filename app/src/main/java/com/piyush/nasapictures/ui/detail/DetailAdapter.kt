package com.piyush.nasapictures.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.RequestManager
import com.piyush.nasapictures.databinding.DetailPhotoItemBinding
import com.piyush.nasapictures.model.PhotoModel
import com.piyush.nasapictures.ui.BaseAdapter

class DetailAdapter(private val requestManager: RequestManager,
                    private val imageLoadListener: ImageLoadListener)
    : BaseAdapter<PhotoModel, DetailPhotoViewHolder>()
{


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailPhotoViewHolder {
        return DetailPhotoViewHolder(
            DetailPhotoItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)).apply {
            binding.imageLoadListener = imageLoadListener
        }
    }

    override fun onBindViewHolder(holder: DetailPhotoViewHolder, position: Int) {
        holder.bind(requestManager, items[position])
    }

}