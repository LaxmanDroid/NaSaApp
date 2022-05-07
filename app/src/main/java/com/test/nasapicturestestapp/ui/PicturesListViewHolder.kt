package com.test.nasapicturestestapp.ui

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.nasapicturestestapp.data.NasaPicture
import com.test.nasapicturestestapp.databinding.ItemListPictureBinding

class PicturesListViewHolder(
    private val itemBinding: ItemListPictureBinding,
    openDetailsClickLister: ((Int) -> Unit)? = null
) :
    RecyclerView.ViewHolder(itemBinding.root) {

    init {
        itemBinding.root.setOnClickListener{
            openDetailsClickLister?.invoke(adapterPosition)
        }
    }

    fun bind(nasaPicture: NasaPicture) {
        Glide.with(itemBinding.ivPicture.context)
            .load(nasaPicture.url)
            .into(itemBinding.ivPicture)
    }
}