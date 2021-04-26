package com.castprogramms.karma.ui.gallery

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.castprogramms.karma.R
import com.castprogramms.karma.data.Result
import com.castprogramms.karma.databinding.BottomSheetBinding
import com.castprogramms.karma.databinding.FragmentProfileBinding
import com.castprogramms.karma.network.Resource
import com.castprogramms.karma.network.ServiceFireStore
import com.castprogramms.karma.ui.adapters.ServicesAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import org.koin.android.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {

    private val profileViewModel: ProfileViewModel by viewModel()
    var id = "id"
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val binding = FragmentProfileBinding.bind(view)
        val adapter = ServicesAdapter()
        binding.recycler.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recycler.adapter = adapter
        profileViewModel.getUserData().observe(viewLifecycleOwner, {
            when(it){
                is Resource.Error -> {

                }
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    profileViewModel.getAllUserServices(it.data?.first!!).observe(viewLifecycleOwner,{
                        when(it){
                            is Resource.Error -> {}
                            is Resource.Loading -> {}
                            is Resource.Success -> {
                                adapter.setService(it.data!!)
                            }
                        }
                    })
                }
            }
        })

        binding.addService.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_addServiceFragment)
        }
        return view
    }
}