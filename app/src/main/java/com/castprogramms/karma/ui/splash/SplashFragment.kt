package com.castprogramms.karma.ui.splash

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.castprogramms.karma.R
import com.castprogramms.karma.databinding.SplashFragmentBinding

class SplashFragment: Fragment(R.layout.splash_fragment) {
    val liveDataTimer = MutableLiveData(false)
    var isStart = MutableLiveData(false)
    val timer = object : CountDownTimer(1000, 1000){
        override fun onTick(millisUntilFinished: Long) {}

        override fun onFinish() {
            liveDataTimer.postValue(true)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = SplashFragmentBinding.bind(view)
        timer.start()
        val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.show_anim)
        val animOval = AnimationUtils.loadAnimation(requireContext(), R.anim.left_right_big_oval)
        animOval.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                isStart.postValue(true)
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })
        isStart.observe(viewLifecycleOwner, {
            if (it) {
                liveDataTimer.observe(viewLifecycleOwner, {
                    if (it && !anim.hasStarted()) {
                        binding.goToEnter.startAnimation(anim)
                        binding.goToRegistration.startAnimation(anim)
                    }
                })
            }
        })
        binding.bigOvvval.startAnimation(animOval)
        binding.bigOvvval.visibility = View.VISIBLE
        anim.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationRepeat(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                binding.goToEnter.visibility = View.VISIBLE
                binding.goToRegistration.visibility = View.VISIBLE
            }

            override fun onAnimationStart(animation: Animation?) {

            }
        })

        binding.goToRegistration.setOnClickListener {
            findNavController().navigate(R.id.action_splashFragment_to_insertDataFragment)
        }
        binding.goToEnter.setOnClickListener {
            findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
        }
    }
}