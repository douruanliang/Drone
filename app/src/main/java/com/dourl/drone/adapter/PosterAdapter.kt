package com.dourl.drone.adapter

import android.view.View
import com.dourl.baserecyclerviewadapter.BaseAdapter
import com.dourl.baserecyclerviewadapter.SectionRow
import com.dourl.drone.R
import com.dourl.drone.model.Poster

class PosterAdapter() : BaseAdapter() {
    init {
        addSection(arrayListOf<Poster>())
    }

    fun addPosterList(posters: List<Poster>) {
        sections().first().run {
            clear()
            addAll(posters)
        }
        notifyDataSetChanged()
    }

    override fun layout(sectionRow: SectionRow): Int = R.layout.item_poster

    override fun viewHolder(layout: Int, view: View) = PosterViewHolder(view)
}