package com.castprogramms.karma.ui.allServices

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.castprogramms.karma.R
import com.castprogramms.karma.databinding.FragmentAllServicesBinding
import com.castprogramms.karma.network.Resource
import com.castprogramms.karma.tools.Service
import com.castprogramms.karma.ui.adapters.ServicesAdapter
import org.koin.android.viewmodel.ext.android.viewModel

class AllServicesFragment : Fragment() {
    private val servicesViewModel : ServicesViewModel by viewModel()
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_all_services, container, false)
        val binding = FragmentAllServicesBinding.bind(view)
        val adapter = ServicesAdapter()
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = GridLayoutManager(requireContext(), 2)
        servicesViewModel.getAllServices().observe(viewLifecycleOwner) {
            when(it){
                is Resource.Error -> {
                    Log.e("data", it.message.toString())
                }
                is Resource.Loading -> {

                    Log.e("data", it.message.toString())
                }
                is Resource.Success -> {
                    if (it.data != null) {
                        adapter.setService(getListServices(it.data))

                    }
                }
            }
        }
        return view
    }

    fun getListServices(list: List<Pair<String, Service>>): Pair<MutableList<Service>, MutableList<String>> {
        val mutableList = mutableListOf<Service>()
        val ids = mutableListOf<String>()
        list.forEach {
            mutableList.add(it.second)
            ids.add(it.first)
        }
        return mutableList to ids
    }
}