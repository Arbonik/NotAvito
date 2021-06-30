package com.castprogramms.karma.ui.adapters

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.MenuRes
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.MutableLiveData
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.squareup.picasso.Picasso
import java.lang.Exception

class MyServicesAdapter(val deleteService:(service: Service) -> Unit): RecyclerView.Adapter<MyServicesAdapter.ProfileServicesViewHolder>() {
    var services = mutableListOf<Service>()
    val mutableLiveDataNeedDelete = MutableLiveData<MutableList<Service>>(mutableListOf())
    fun setService(list: List<Service>){
        services = list.toMutableList()
        notifyDataSetChanged()
    }

    fun addService(service: Service){
        services.add(service)
        notifyDataSetChanged()

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileServicesViewHolder {
        return ProfileServicesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_services, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ProfileServicesViewHolder, position: Int) {
        holder.onbind(services[position])
    }

    override fun getItemCount() = services.size

    inner class ProfileServicesViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binding = ItemServicesBinding.bind(itemView)
        fun onbind(service: Service){
            binding.cost.text = service.cost.toString() + "₽"
            binding.name.text = service.name
            binding.nameAuthor.text = service.desc
            binding.time.text = TimeModule.getServiceTime(service.dataTime)
            binding.unit.text = "/" + service.unit
            try {
                Glide.with(itemView)
                    .load(service.photo).fitCenter()
                    .addListener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean,
                        ): Boolean {
                            binding.photo.setImageDrawable(itemView.context.resources.getDrawable(R.drawable.no_photo))
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
            }catch (e: Exception){
                Log.e("data", e.message.toString())
                Picasso.get()
                    .load(R.drawable.ic_launcher_foreground)
                    .into(binding.photo)
            }
            binding.root.setOnLongClickListener {
                showMenu(it, R.menu.card_option_menu, service)
                true
            }
        }
        fun showMenu(v: View, @MenuRes menuRes: Int, service: Service) {
            val popup = PopupMenu(itemView.context, v)
            popup.menuInflater.inflate(menuRes, popup.menu)
            popup.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.opt_edit ->{

                    }
                    R.id.opt_delete ->{
                        MaterialAlertDialogBuilder(itemView.context,  R.style.MaterialAlertDialog_OK_color)
                            .setTitle("Вы хотите удалить услугу?")
                            .setNegativeButton("Отмена") { dialog, which -> }
                            .setPositiveButton("Да") { dialog, which ->
                                deleteService(service)
                            }
                            .show()
//                        mutableLiveDataNeedDelete.postValue(mutableLiveDataNeedDelete.value?.apply { add(service) })
                    }
                }
                true
            }
            popup.setOnDismissListener {

            }
            popup.show()
        }
    }
}