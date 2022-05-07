package com.test.nasapicturestestapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.nasapicturestestapp.data.NasaPicture
import com.test.nasapicturestestapp.databinding.ItemListPictureBinding


class PicturesListAdapter(private var list: List<NasaPicture>) :
    RecyclerView.Adapter<PicturesListViewHolder>() {

    var openDetailsClickLister: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PicturesListViewHolder {
        val itemBinding =
            ItemListPictureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PicturesListViewHolder(itemBinding, openDetailsClickLister)
    }

    override fun onBindViewHolder(holder: PicturesListViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun refreshList(nasaPicturesList: List<NasaPicture>) {
        list = nasaPicturesList
        notifyDataSetChanged()
    }

}