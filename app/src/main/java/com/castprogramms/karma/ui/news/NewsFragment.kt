package com.castprogramms.karma.ui.news

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.castprogramms.karma.R
import com.castprogramms.karma.databinding.FragmentNewsBinding
import com.castprogramms.karma.network.Resource
import com.castprogramms.karma.tools.New
import com.castprogramms.karma.ui.adapters.NewsAdapter
import org.koin.android.viewmodel.ext.android.viewModel

class NewsFragment: Fragment(R.layout.fragment_news) {
    private val newsViewModel: NewsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentNewsBinding.bind(view)
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        val adapter = NewsAdapter()
        binding.recycler.adapter = adapter
        newsViewModel.getAllNews().observe(viewLifecycleOwner, {
            when(it){
                is Resource.Error -> {

                }
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    if (it.data != null)
                        adapter.news = it.data.toMutableList()
                }
            }
        })
    }
}