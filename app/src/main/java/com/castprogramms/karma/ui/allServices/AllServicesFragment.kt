package com.castprogramms.karma.ui.allServices

import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.AccelerateInterpolator
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import com.castprogramms.karma.R
import com.castprogramms.karma.databinding.FragmentAllServicesBinding
import com.castprogramms.karma.network.Resource
import com.castprogramms.karma.tools.Score
import com.castprogramms.karma.tools.Service
import com.castprogramms.karma.ui.adapters.ServicesAdapter
import com.google.android.material.textview.MaterialTextView
import jp.wasabeef.recyclerview.animators.ScaleInTopAnimator
import org.koin.android.viewmodel.ext.android.viewModel

class AllServicesFragment : Fragment() {
    private val servicesViewModel : ServicesViewModel by viewModel()
    lateinit var textView: MaterialTextView
    val adapter = ServicesAdapter({showOrHideEmpty(it)})
                    { s: String, score: Score -> servicesViewModel.addScoreUser(s,score) }
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_all_services, container, false)
        val binding = FragmentAllServicesBinding.bind(view)
        this.setHasOptionsMenu(true)
        textView = binding.empty
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recycler.itemAnimator = ScaleInTopAnimator(AccelerateInterpolator()).apply {
            this.addDuration = 200

        }
        servicesViewModel.getAllServices().observe(viewLifecycleOwner) {
            when(it){
                is Resource.Error -> {
                    Log.e("data", it.message.toString())
                }
                is Resource.Loading -> {

                    Log.e("data", it.message.toString())
                }
                is Resource.Success -> {
                    if (it.data != null) {
                        adapter.setService(getListServices(it.data))
                    }
                }
            }
        }
        return view
    }

    fun getListServices(list: List<Pair<String, Service>>): Pair<MutableList<Service>, MutableList<String>> {
        val mutableList = mutableListOf<Service>()
        val ids = mutableListOf<String>()
        list.forEach {
            mutableList.add(it.second)
            ids.add(it.first)
        }
        return mutableList to ids
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_service, menu)
        val search = menu.findItem(R.id.search_service).actionView as SearchView
        search.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.e("data", newText.toString())
                if (newText != null)
                    adapter.filter.filter(newText)
                return false
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    fun showOrHideEmpty(show: Boolean){
        if (show)
            textView.visibility = View.VISIBLE
        else
            textView.visibility = View.GONE
    }
}