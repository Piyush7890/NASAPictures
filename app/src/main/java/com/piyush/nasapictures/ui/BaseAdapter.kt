package com.piyush.nasapictures.ui

import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T : Any, V : RecyclerView.ViewHolder> : RecyclerView.Adapter<V>()
{
    var items : List<T> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = items.size

}