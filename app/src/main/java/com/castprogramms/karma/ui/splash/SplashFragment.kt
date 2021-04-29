package com.castprogramms.karma.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.castprogramms.karma.R
import com.castprogramms.karma.databinding.SplashFragmentBinding

class SplashFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = inflater.inflate(R.layout.splash_fragment, container, false)
        val binding = SplashFragmentBinding.bind(view)
//        binding.root.setOnClickListener {
        binding.goToEnter.visibility = View.VISIBLE
        binding.goToRegistration.visibility = View.VISIBLE
//        } Потом можно сделать либо появление после анимации, либо со временем
        binding.goToRegistration.setOnClickListener {
            findNavController().navigate(R.id.action_splashFragment_to_insertDataFragment)
        }
        binding.goToEnter.setOnClickListener {
            findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
        }
        return view
    }

}