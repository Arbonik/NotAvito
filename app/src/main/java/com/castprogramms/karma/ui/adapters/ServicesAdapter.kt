package com.castprogramms.karma.ui.adapters

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.castprogramms.karma.R
import com.castprogramms.karma.databinding.ItemServicesBinding
import com.castprogramms.karma.tools.Service
import com.castprogramms.karma.tools.time.DataTime
import com.castprogramms.karma.tools.time.TimeModule
import com.squareup.picasso.Picasso
import java.lang.Exception
import java.util.*

class ServicesAdapter(val showEmpty:(boolean: Boolean) -> Unit) : RecyclerView.Adapter<ServicesAdapter.ServicesViewHolder>(), Filterable {
    var services = mutableListOf<Service>()
    var ids = mutableListOf<String>()
    var sortedServices = mutableListOf<Service>()
    var sortedIDs = mutableListOf<String>()
    fun setService(list: Pair<List<Service>, List<String>>){
        val sortedList = sorted(list)
        services = sortedList.first.toMutableList()
        ids = sortedList.second.toMutableList()
        sortedServices = services
        sortedIDs = ids
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
        holder.onbind(sortedServices[position], sortedIDs[position])
    }

    override fun getItemCount() = sortedServices.size

    inner class ServicesViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binding = ItemServicesBinding.bind(itemView)
        fun onbind(service: Service, id: String){
            binding.cost.text = service.cost.toString()
            binding.name.text = service.name
            binding.nameAuthor.text = service.desc
            binding.time.text = TimeModule.getServiceTime(service.dataTime)
            binding.unit.text = "₽/" + service.unit
            try {
                if (service.photo != "")
                Glide.with(itemView)
                    .load(service.photo).fitCenter()
//                    .apply(RequestOptions.overrideOf(100,100))
//                    .apply(RequestOptions.fitCenterTransform())
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

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                sortedServices = mutableListOf()
                sortedIDs = mutableListOf()
                val isContains = mutableListOf<Boolean>()
                services.forEach {
                    if (it.name.contains(constraint.toString(), true))
                        isContains.add(true)
                    else
                        isContains.add(false)
                }
                for (i in services.indices)
                    if (isContains[i]) {
                        sortedServices.add(services[i])
                        sortedIDs.add(ids[i])
                    }
                return FilterResults().apply { values = sortedServices to sortedIDs}
            }
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results != null) {
                    val values = results.values as Pair<MutableList<Service>, MutableList<String>>
                    sortedServices = values.first
                    sortedIDs = values.second
                    if (sortedServices.isEmpty())
                        showEmpty(true)
                    else
                        showEmpty(false)
                    notifyDataSetChanged()
                }
            }
        }
    }
    fun sorted(pair: Pair<List<Service>, List<String>>): Pair<List<Service>, List<String>> {
        var mutableMap = mutableMapOf<Service, String>()
        for (i in pair.first.indices)
            mutableMap.put(pair.first[i], pair.second[i])
        mutableMap = mutableMap.toList().sortedWith( compareBy {
            getSortData(it.first.dataTime)
        }).reversed().toMap().toMutableMap()
        return mutableMap.keys.toList() to mutableMap.values.toList()
    }

    fun getSortData(dataTime: DataTime):String{
        var string = ""
        string += dataTime.year.toString()
        if (dataTime.mouth<10)
            string += "0${dataTime.mouth}"
        else
            string += dataTime.mouth.toString()
        if (dataTime.day < 10)
            string += "0${dataTime.day}"
        else
            string += dataTime.day.toString()
        val array = dataTime.time.split(':')
        array.forEach {
            if (it.toInt() < 10)
                string += "0$it"
            else
                string += it
        }
        return string
    }
}