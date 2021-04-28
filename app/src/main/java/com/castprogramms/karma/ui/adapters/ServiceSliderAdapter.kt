package com.castprogramms.karma.ui.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.castprogramms.karma.R
import com.castprogramms.karma.databinding.ItemSliderBinding
import com.smarteist.autoimageslider.SliderViewAdapter

class ServiceSliderAdapter: SliderViewAdapter<ServiceSliderAdapter.ServiceSliderViewHolder>() {
    val pictures = mutableListOf<Uri>(Uri.parse("https://firebasestorage.googleapis.com/v0/b/karma-a96b8.appspot.com/o/images%2F2640?alt=media&token=023a6d80-f020-42d2-9f71-722afe17fed7"),
    Uri.parse("https://firebasestorage.googleapis.com/v0/b/karma-a96b8.appspot.com/o/images%2F149009?alt=media&token=014a2ce6-9c9a-4c97-baad-5610249a84d6"))
    override fun getCount() = pictures.size

    override fun onCreateViewHolder(parent: ViewGroup): ServiceSliderViewHolder {
        return ServiceSliderViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_slider, parent, false)
        )
    }

    override fun onBindViewHolder(viewHolder: ServiceSliderViewHolder, position: Int) {
        viewHolder.bing(pictures[position])
    }

    inner class ServiceSliderViewHolder(view: View): SliderViewAdapter.ViewHolder(view){
        val binding = ItemSliderBinding.bind(view)
        fun bing(picture: Uri){
            Glide.with(itemView)
                .load(picture)
                .fitCenter()
                .into(binding.ivAutoImageSlider)
        }
    }
}