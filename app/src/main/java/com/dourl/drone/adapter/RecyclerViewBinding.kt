package com.dourl.drone.adapter

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dourl.baserecyclerviewadapter.BaseAdapter
import com.dourl.drone.model.Poster


@BindingAdapter("adapter")
fun bindAdapter(view: RecyclerView, baseAdapter: BaseAdapter) {
    view.adapter = baseAdapter
}

@BindingAdapter("adapterPosterList")
fun bindAdapterPosterList(view: RecyclerView, posters: List<Poster>?) {
    if (!posters.isNullOrEmpty()) {
        (view.adapter as PosterAdapter)?.addPosterList(posters)
    }
}

@BindingAdapter("loadImage")
fun bindLoadImage(view: AppCompatImageView, url: String) {
    Glide.with(view.context).load(url).into(view)
}