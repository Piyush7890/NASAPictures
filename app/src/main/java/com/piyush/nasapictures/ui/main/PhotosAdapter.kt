package com.piyush.nasapictures.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.piyush.nasapictures.databinding.PhotoItemBinding
import com.piyush.nasapictures.model.PhotoModel

class PhotosAdapter(private val requestManager: RequestManager)
    : RecyclerView.Adapter<PhotoViewHolder>() {


    var photos : List<PhotoModel> = ArrayList()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return    PhotoViewHolder(PhotoItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun getItemCount() = photos.size

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(requestManager, photos[position])
    }
}