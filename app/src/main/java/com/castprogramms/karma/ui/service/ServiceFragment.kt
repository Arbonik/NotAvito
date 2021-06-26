package com.castprogramms.karma.ui.service

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.castprogramms.karma.MainActivity
import com.castprogramms.karma.R
import com.castprogramms.karma.databinding.FragmentServiceBinding
import com.castprogramms.karma.network.Resource
import com.castprogramms.karma.tools.Score
import com.castprogramms.karma.ui.adapters.ServiceSliderAdapter
import com.google.android.material.snackbar.Snackbar
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import org.koin.android.viewmodel.ext.android.viewModel

class ServiceFragment : Fragment(R.layout.fragment_service) {
    private val serviceViewModel : ServiceViewModel by viewModel()
    var id = ""
    var idAuthor = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null){
            id = arguments?.getString("id").toString()
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentServiceBinding.bind(view)
        if (id != "" && id != "null")
            serviceViewModel.loadService(id)
        observeService(binding)
        observeUserData(binding)
        binding.imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM)
        binding.imageSlider.scrollTimeInSec = 1
        binding.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        binding.imageSlider.startAutoCycle()
        profileViewListener(binding)
        likeButtonsListeners(binding)
    }

    private fun likeButtonsListeners(binding: FragmentServiceBinding) {
        binding.likeButtons.bigLike.setOnClickListener {
            Log.e("data", "bl")
            val mutableLiveData = serviceViewModel.addScoreUser(idAuthor, Score(20, id, serviceViewModel.getCurrentUser()?.uid.toString()))
            mutableLiveData.observe(viewLifecycleOwner) {
                when(it){
                    is Resource.Error -> {
                        createSnackBar(it.message.toString())
                    }
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        createSnackBar(it.data.toString())
                    }
                }
            }
        }
        binding.likeButtons.smallLike.setOnClickListener {
            Log.e("data", "sl")
            val mutableLiveData = serviceViewModel.addScoreUser(idAuthor, Score(10, id, serviceViewModel.getCurrentUser()?.uid.toString()))
            mutableLiveData.observe(viewLifecycleOwner) {
                when(it){
                    is Resource.Error -> {
                        createSnackBar(it.message.toString())
                    }
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        createSnackBar(it.data.toString())
                    }
                }
            }
        }
        binding.likeButtons.bigDislike.setOnClickListener {
            Log.e("data", "bd")
            val mutableLiveData = serviceViewModel.addScoreUser(idAuthor, Score(-20, id, serviceViewModel.getCurrentUser()?.uid.toString()))
            mutableLiveData.observe(viewLifecycleOwner) {
                when(it){
                    is Resource.Error -> {
                        createSnackBar(it.message.toString())
                    }
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        createSnackBar(it.data.toString())
                    }
                }
            }
        }
        binding.likeButtons.smallDislike.setOnClickListener {
            Log.e("data", "sd")
            val mutableLiveData = serviceViewModel.addScoreUser(idAuthor, Score(-10, id, serviceViewModel.getCurrentUser()?.uid.toString()))
            mutableLiveData.observe(viewLifecycleOwner) {
                when(it){
                    is Resource.Error -> {
                        createSnackBar(it.message.toString())
                    }
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        createSnackBar(it.data.toString())
                    }
                }
            }
        }
    }

    private fun profileViewListener(binding: FragmentServiceBinding) {
        binding.profileView.setOnClickListener {
            findNavController().navigate(
                R.id.action_serviceFragment_to_profileUserFragment,
                bundleOf("id" to idAuthor)
            )
        }
    }

    private fun observeUserData(binding: FragmentServiceBinding) {
        serviceViewModel.liveDataUserData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {
                }
                is Resource.Loading -> {
                    binding.allProgressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.allProgressBar.visibility = View.GONE
                    val pair = it.data
                    if (pair != null) {
                        binding.nameUser.text = pair.second.getFullName()
                        binding.numberUser.text = pair.second.number
                    }
                }
            }
        }
    }

    private fun observeService(binding: FragmentServiceBinding) {
        serviceViewModel.liveDataService.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {
                }
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    val service = it.data
                    if (service != null) {
                        serviceViewModel.loadUserData(service.idAuthor)
                        this.idAuthor = service.idAuthor
                        binding.nameShowServ.text = service.name
                        binding.costShowServ.text = service.cost.toString() + "₽/" + service.unit
                        binding.descShowServ.text = service.desc
                        binding.imageSlider.setSliderAdapter(ServiceSliderAdapter().apply {
                            setUris(listOf(Uri.parse(service.photo)))
                        })

                    }
                }
            }
        }

        serviceViewModel.liveDataUserData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {  }
                is Resource.Loading -> {
                        binding.allProgressBar.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.allProgressBar.visibility = View.GONE
                        val pair = it.data
                        if (pair != null) {
                            binding.nameUser.text = pair.second.getFullName()
                            binding.numberUser.text = pair.second.number
                        }
                    }
                }
        }
        binding.imageSlider
        binding.imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM)
        binding.imageSlider.scrollTimeInSec = 1
        binding.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        binding.imageSlider.startAutoCycle()

        binding.call.setOnClickListener {
            if (checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                serviceViewModel.liveDataUserData.observe(viewLifecycleOwner, {
                    if (it is Resource.Success) {
                        startActivity(Intent(Intent.ACTION_CALL,
                            Uri.parse("tel:" + it.data?.second?.number)))
                    }
                })
            }
            else{
                createSnackBar("Дайте разрешение на звонки в настройках приложения")
            }
        }

        binding.profileView.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("id", idAuthor)
            findNavController().navigate(R.id.action_serviceFragment_to_profileUserFragment, bundle)
        }
    }

    private fun createSnackBar(message: String){
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }
}