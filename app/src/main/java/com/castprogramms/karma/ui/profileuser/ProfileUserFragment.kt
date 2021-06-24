package com.castprogramms.karma.ui.profileuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.castprogramms.karma.R
import com.castprogramms.karma.databinding.ProfileUserFragmentBinding
import com.castprogramms.karma.network.Resource
import com.castprogramms.karma.tools.Score
import com.castprogramms.karma.tools.Service
import com.castprogramms.karma.ui.adapters.ServicesAdapter
import org.koin.android.viewmodel.ext.android.viewModel

class ProfileUserFragment : Fragment() {
    private val profileUserViewModel: ProfileUserViewModel by viewModel()
    var idAuthor = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            idAuthor = arguments?.getString("id").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.profile_user_fragment, container, false)
        val binding = ProfileUserFragmentBinding.bind(view)
        val adapter = ServicesAdapter({}, {_,_ ->  }, true)
        binding.myRecServiceProfile.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.myRecServiceProfile.adapter = adapter

        profileUserViewModel.getUserServices(idAuthor).observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> { }
                is Resource.Loading -> { }
                is Resource.Success -> {
                    adapter.setService(getListServices(it.data!!))
                }
            }
        }

        profileUserViewModel.getUserData(idAuthor).observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Error -> { }
                is Resource.Loading -> { }
                is Resource.Success -> {
                    val user = it.data?.second
                    if (user != null) {
                        binding.nameUserProfile.text = user.getFullName()
                        binding.descUserProfile.text = "Карма: " + user.scores.toString()
                    }
                }
            }
        })
        return view
    }

    private fun getListServices(list: List<Pair<String, Service>>): Pair<MutableList<Service>, MutableList<String>> {
        val mutableList = mutableListOf<Service>()
        val ids = mutableListOf<String>()
        list.forEach {
            mutableList.add(it.second)
            ids.add(it.first)
        }
        return mutableList to ids
    }

    private fun countScores(scores: List<Score>): Int{
        var sum = 0
        scores.forEach {
            sum += it.value
        }
        return sum
    }
}