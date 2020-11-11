package com.piyush.nasapictures.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.res.use
import com.piyush.nasapictures.R
import com.piyush.nasapictures.databinding.InfoViewBinding

class InfoView @JvmOverloads constructor(context: Context,
                                        attrs: AttributeSet?=null,
                                        defStyleAttr: Int = 0,
                                        defStyleRes: Int=0 ) :
    LinearLayout(context, attrs, defStyleAttr, defStyleRes)
{

    private var binding : InfoViewBinding =
        InfoViewBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        context.obtainStyledAttributes(attrs, R.styleable.InfoView).use {
            val title = it.getString(R.styleable.InfoView_label)
            binding.label.text = title

            val subtitle = it.getString(R.styleable.InfoView_value)
            binding.value.text = subtitle
            orientation = VERTICAL
        }


    }

    fun setValue(value : String?)
    {
        binding.value.text = value
    }
}