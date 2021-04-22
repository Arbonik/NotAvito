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
import com.castprogramms.karma.R
import com.castprogramms.karma.databinding.FragmentProfileBinding
import com.castprogramms.karma.network.Resource
import org.koin.android.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {

    private val profileViewModel: ProfileViewModel by viewModel()
    var id = ""
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val binding = FragmentProfileBinding.bind(view)
        profileViewModel.getUser(id).observe(viewLifecycleOwner, {
            when(it){
                is Resource.Error -> {
                    Log.e("Data", it.message.toString())
                }
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    if (it.data != null)
                        Log.e("data", it.data.toString())
                }
            }
        })
        return view
    }
}