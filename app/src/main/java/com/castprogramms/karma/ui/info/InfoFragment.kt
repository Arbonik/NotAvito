package com.castprogramms.karma.ui.info

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.castprogramms.karma.R
import com.castprogramms.karma.databinding.FragmentInfoBinding
import com.castprogramms.karma.network.Resource
import org.koin.android.viewmodel.ext.android.viewModel

class InfoFragment: Fragment(R.layout.fragment_info) {
    val viewModel: InfoViewModel by viewModel()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentInfoBinding.bind(view)
        viewModel.getInfo().observe(viewLifecycleOwner, {
            when(it){
                is Resource.Error ->{
                   binding.info.setTextColor(Color.RED)
                   binding.info.text = it.message
                    binding.progressInfo.visibility = View.INVISIBLE
                }
                is Resource.Loading -> {
                    binding.progressInfo.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.info.setTextColor(Color.BLACK)
                    binding.info.text = it.data
                    binding.progressInfo.visibility = View.INVISIBLE
                }
            }
        })
    }
}