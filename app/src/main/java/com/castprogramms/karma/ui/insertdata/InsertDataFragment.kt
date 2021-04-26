package com.castprogramms.karma.ui.insertdata

import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.castprogramms.karma.R
import com.castprogramms.karma.databinding.FragmentInsertDataBinding
import com.castprogramms.karma.tools.User
import org.koin.android.ext.android.inject

class InsertDataFragment: Fragment() {
    private val insertDataViewModel : InsertDataViewModel by inject()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_insert_data, container, false)
        val binding = FragmentInsertDataBinding.bind(view)
        binding.registration.setOnClickListener {
            insertDataViewModel.addUser(User(
                binding.name.text.toString(),
                binding.family.text.toString(),
                binding.number.text.toString()
            ), binding.email.text.toString(), binding.password.toString())
        }
        return view
    }
}