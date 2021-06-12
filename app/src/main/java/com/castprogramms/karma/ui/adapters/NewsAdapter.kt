package com.castprogramms.karma.ui.adapters

import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.castprogramms.karma.R
import com.castprogramms.karma.databinding.ItemNewsBinding
import com.castprogramms.karma.tools.New

class NewsAdapter: RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    var news = mutableListOf<New>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(news[position])
    }

    override fun getItemCount() = news.size

    inner class NewsViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binging = ItemNewsBinding.bind(view)
        fun bind(new: New){
            binging.nameNew.text = new.name
            binging.descNew.text = new.desc
            Glide.with(itemView)
                .load(Uri.parse(new.photo))
                .addListener(object :RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean,
                    ): Boolean {
                        return false
                    }
                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean,
                    ): Boolean {
                        binging.photoNews.setImageDrawable(resource)
                        return false
                    }
                }).into(binging.photoNews)
        }
    }
}