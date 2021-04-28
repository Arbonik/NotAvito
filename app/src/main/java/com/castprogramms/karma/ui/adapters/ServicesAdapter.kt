package com.castprogramms.karma.ui.adapters

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.castprogramms.karma.R
import com.castprogramms.karma.databinding.ItemServicesBinding
import com.castprogramms.karma.tools.Service
import com.castprogramms.karma.tools.time.TimeModule
import com.squareup.picasso.Picasso
import java.lang.Exception

class ServicesAdapter: RecyclerView.Adapter<ServicesAdapter.ServicesViewHolder>() {
    var services = mutableListOf<Service>()
    var ids = mutableListOf<String>()
    fun setService(list: Pair<List<Service>, List<String>>){
        services = list.first.toMutableList()
        ids = list.second.toMutableList()
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
        holder.onbind(services[position], ids[position])
    }

    override fun getItemCount() = services.size

    inner class ServicesViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binding = ItemServicesBinding.bind(itemView)
        fun onbind(service: Service, id: String){
            binding.cost.text = service.cost.toString()
            binding.name.text = service.name
            binding.nameAuthor.text = service.desc
            binding.time.text = TimeModule.getServiceTime(service.dataTime)
            try {
                if (service.photo != "")
                Glide.with(itemView)
                        .load(service.photo).fitCenter()
                    .addListener(object :RequestListener<Drawable>{
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean,
                        ): Boolean {
                            binding.progressBar2.visibility = View.GONE
                            return true
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean,
                        ): Boolean {
                            binding.photo.setImageDrawable(resource)
                            binding.progressBar2.visibility = View.GONE
                            return true
                        }
                    })
                        .into(binding.photo)
                else
                    binding.progressBar2.visibility = View.GONE
            }catch (e: Exception){
                Picasso.get()
                    .load(R.drawable.ic_launcher_foreground)
                    .into(binding.photo)
            }
            binding.root.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("id", id)
                itemView.findNavController().navigate(R.id.action_allServicesFragment_to_serviceFragment, bundle)
            }
        }
    }
}