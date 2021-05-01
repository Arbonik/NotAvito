package com.castprogramms.karma.ui.progfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.castprogramms.karma.R
import com.castprogramms.karma.databinding.FragmentProfileBinding
import com.castprogramms.karma.network.Resource
import com.castprogramms.karma.ui.adapters.ProfileServicesAdapter
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
        val adapter = ProfileServicesAdapter { profileViewModel.deleteService(it) }
        binding.recycler.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recycler.adapter = adapter
        profileViewModel.getUserData().observe(viewLifecycleOwner, Observer{
            when(it){
                is Resource.Error -> {

                }
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    profileViewModel.getAllUserServices(it.data?.first!!).observe(viewLifecycleOwner) {
                        when(it){
                            is Resource.Error -> {}
                            is Resource.Loading -> {}
                            is Resource.Success -> {
                                adapter.setService(it.data!!)
                            }
                        }
                    }
                }
            }
        })
        binding.addService.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_addServiceFragment)
        }
        return view
    }
}