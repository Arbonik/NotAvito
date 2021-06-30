package com.castprogramms.karma.ui.donat

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.castprogramms.karma.R
import com.castprogramms.karma.databinding.FragmentDonatBinding
import com.castprogramms.karma.network.Resource
import org.koin.android.viewmodel.ext.android.viewModel

class DonatFragment: Fragment(R.layout.fragment_donat) {
    val viewModel : DonatViewModel by viewModel()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentDonatBinding.bind(view)
        viewModel.getInfoAboutDonat().observe(viewLifecycleOwner, {
            when(it){
                is Resource.Error -> {
                    binding.progressDonat.visibility = View.GONE
                    binding.donat.text = it.message
                }
                is Resource.Loading -> {
                    binding.progressDonat.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressDonat.visibility = View.GONE
                    binding.donat.text = it.data
                }
            }
        })
    }
}