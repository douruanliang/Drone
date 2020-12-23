package com.dourl.drone.adapter

import android.view.View
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.dourl.baserecyclerviewadapter.BaseViewHolder
import com.dourl.drone.databinding.ItemPosterBinding
import com.dourl.drone.model.Poster

class PosterViewHolder(view: View):BaseViewHolder(view) {

    private lateinit var data: Poster
    private val binding:ItemPosterBinding by bindings(view)


    override fun bindData(data: Any) {

        if (data is Poster){
            this.data = data
            drwaItemUI()
        }
    }

    private fun drwaItemUI() {
        binding.apply {
            ViewCompat.setTransitionName(binding.itemContainer,data.name)
            poster = data
            executePendingBindings()
        }
    }

    override fun onClick(v: View?) = Unit

    override fun onLongClick(v: View?) = false


    inline fun <reified  T : ViewDataBinding> bindings(view:View):Lazy<T> = lazy {
        requireNotNull(DataBindingUtil.bind<T>(view)){
            "cannot find the matched view to layout."
        }
    }
}