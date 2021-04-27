package com.castprogramms.karma.ui.adapters

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.MenuRes
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.graphics.drawable.toIcon
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.castprogramms.karma.R
import com.castprogramms.karma.databinding.ItemServicesBinding
import com.castprogramms.karma.tools.Service
import com.castprogramms.karma.tools.time.TimeModule
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import java.lang.Exception

class ServicesAdapter: RecyclerView.Adapter<ServicesAdapter.ServicesViewHolder>() {
    var services = mutableListOf<Service>()
    fun setService(list: List<Service>){
        services = list.toMutableList()
        notifyDataSetChanged()
    }

    fun addService(service: Service){
        services.add(service)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicesViewHolder {
        return ServicesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_services, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ServicesViewHolder, position: Int) {
        holder.onbind(services[position])
    }

    override fun getItemCount() = services.size

    inner class ServicesViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binding = ItemServicesBinding.bind(itemView)
        fun onbind(service: Service){
            binding.cost.text = service.cost.toString()
            binding.name.text = service.name
            binding.nameAuthor.text = service.desc
            binding.time.text = TimeModule.getServiceTime(service.dataTime)
            try {
                Glide.with(itemView)
                        .load(service.photo)
                        .into(binding.photo)
            }catch (e: Exception){
                Picasso.get()
                    .load(R.drawable.ic_launcher_foreground)
                    .into(binding.photo)
            }
        }

    }
}