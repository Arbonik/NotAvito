package com.castprogramms.karma.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.castprogramms.karma.R
import com.castprogramms.karma.databinding.FragmentServiceBinding
import com.castprogramms.karma.ui.adapters.ServiceSliderAdapter
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations

class ServiceFragment : Fragment(R.layout.fragment_service) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentServiceBinding.bind(view)
        binding.imageSlider.setSliderAdapter(ServiceSliderAdapter())
        binding.imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM)
        binding.imageSlider.scrollTimeInSec = 4
        binding.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        binding.imageSlider.startAutoCycle()
    }

}