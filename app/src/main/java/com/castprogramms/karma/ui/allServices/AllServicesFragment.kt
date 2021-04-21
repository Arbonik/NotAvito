package com.castprogramms.karma.ui.allServices

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.castprogramms.karma.R
import com.castprogramms.karma.databinding.FragmentAllServicesBinding
import com.castprogramms.karma.network.Resource
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
        servicesViewModel.getAllServices().observe(viewLifecycleOwner, {
            when(it){
                is Resource.Error -> {
                    Log.e("data", it.message.toString())
                }
                is Resource.Loading -> {
                    Log.e("data", it.message.toString())
                }
                is Resource.Success -> {
                    if (it.data != null) {
                        adapter.setService(it.data)
                    }
                }
            }
        })
        binding.addService.setOnClickListener {
            findNavController().navigate(R.id.action_allServicesFragment_to_addServiceFragment)
        }
        return view
    }
}