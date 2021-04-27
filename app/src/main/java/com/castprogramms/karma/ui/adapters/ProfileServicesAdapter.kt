package com.castprogramms.karma.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.MenuRes
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.castprogramms.karma.R
import com.castprogramms.karma.databinding.ItemServicesBinding
import com.castprogramms.karma.tools.Service
import com.castprogramms.karma.tools.time.TimeModule
import com.squareup.picasso.Picasso
import java.lang.Exception

class ProfileServicesAdapter: RecyclerView.Adapter<ProfileServicesAdapter.ProfileServicesViewHolder>() {
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
            binding.cost.text = service.cost.toString()
            binding.name.text = service.name
            binding.nameAuthor.text = service.desc
            binding.time.text = TimeModule.getServiceTime(service.dataTime)
            try {
                Glide.with(itemView)
                    .load(service.photo)
                    .into(binding.photo)
            }catch (e: Exception){
                Log.e("data", e.message.toString())
                Picasso.get()
                    .load(R.drawable.ic_launcher_foreground)
                    .into(binding.photo)
            }
            binding.root.setOnLongClickListener {
                // надо сделать всплывающее меню снизу с кнопками Редактровать и Удалить
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
                        mutableLiveDataNeedDelete.postValue(mutableLiveDataNeedDelete.value?.apply { add(service) })
                    }
                }
                true
            }
            popup.setOnDismissListener {
                // Respond to popup being dismissed.
            }
            // Show the popup menu.
            popup.show()
        }
    }
}