package com.castprogramms.karma.ui.profileuser

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.castprogramms.karma.R
import com.castprogramms.karma.databinding.ProfileUserFragmentBinding
import com.castprogramms.karma.network.Resource
import com.castprogramms.karma.ui.adapters.MyServicesAdapter
import com.castprogramms.karma.ui.adapters.ServiceSliderAdapter
import com.castprogramms.karma.ui.adapters.ServicesAdapter
import org.koin.android.viewmodel.ext.android.viewModel

class ProfileUserFragment : Fragment() {
    private val profileUserViewModel: ProfileUserViewModel by viewModel()
    var idAuthor = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null){
            idAuthor = arguments?.getString("id").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view =  inflater.inflate(R.layout.profile_user_fragment, container, false)
        val binding = ProfileUserFragmentBinding.bind(view)
        val adapter = ServicesAdapter()
        binding.myRecServiceProfile.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.myRecServiceProfile.adapter = adapter
        profileUserViewModel.getUserServices(idAuthor).observe(viewLifecycleOwner){
            when(it){
                is Resource.Error -> { }
                is Resource.Loading -> { }
                is Resource.Success -> {
                    //adapter.setService(it.data!!)
                }
            }
        }
        return view
    }
}