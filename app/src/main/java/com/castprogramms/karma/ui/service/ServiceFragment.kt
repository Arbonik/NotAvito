package com.castprogramms.karma.ui.service

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.castprogramms.karma.R
import com.castprogramms.karma.databinding.FragmentServiceBinding
import com.castprogramms.karma.network.Resource
import com.castprogramms.karma.ui.adapters.ServiceSliderAdapter
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
        serviceViewModel.liveDataService.observe(viewLifecycleOwner){
            when(it){
                is Resource.Error -> { }
                is Resource.Loading -> { }
                is Resource.Success -> {
                    val service = it.data
                    if (service != null){
                        serviceViewModel.loadUserData(service.idAuthor)
                        this.idAuthor = service.idAuthor
                        binding.nameShowServ.text = service.name
                        binding.costShowServ.text = service.cost.toString() + "/" + service.unit.toString()
                        binding.descShowServ.text = service.desc
                        binding.imageSlider.setSliderAdapter(ServiceSliderAdapter().apply {
                            setUris(listOf(Uri.parse(service.photo)))
                        })

                    }
                }
            }
        }.runCatching {
            Log.e("data", "te")
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
        }
        binding.imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM)
        binding.imageSlider.scrollTimeInSec = 4
        binding.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        binding.imageSlider.startAutoCycle()

        binding.profileView.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("id", idAuthor)
            findNavController().navigate(R.id.action_serviceFragment_to_profileUserFragment, bundle)
        }
    }
}