package com.castprogramms.karma.ui.adapters

import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.castprogramms.karma.R
import com.castprogramms.karma.databinding.ItemSliderBinding
import com.smarteist.autoimageslider.SliderViewAdapter

class ServiceSliderAdapter: SliderViewAdapter<ServiceSliderAdapter.ServiceSliderViewHolder>() {
    val pictures = mutableListOf<Uri>(Uri.parse("https://firebasestorage.googleapis.com/v0/b/karma-a96b8.appspot.com/o/images%2F2640?alt=media&token=023a6d80-f020-42d2-9f71-722afe17fed7"),
    Uri.parse("https://firebasestorage.googleapis.com/v0/b/karma-a96b8.appspot.com/o/images%2F149009?alt=media&token=014a2ce6-9c9a-4c97-baad-5610249a84d6"))
    fun setUris(list:List<Uri>){
        pictures.clear()
        pictures.addAll(list)
        notifyDataSetChanged()
    }

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
                .addListener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean,
                ): Boolean {
                    binding.sliderProgressBar.visibility = View.GONE
                    return true
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean,
                ): Boolean {
                    binding.ivAutoImageSlider.setImageDrawable(resource)
                    binding.sliderProgressBar.visibility = View.GONE
                    return true
                }
            })
                .fitCenter()
                .into(binding.ivAutoImageSlider)
        }
    }
}