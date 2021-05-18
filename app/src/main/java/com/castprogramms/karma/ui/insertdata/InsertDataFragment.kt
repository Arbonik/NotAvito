package com.castprogramms.karma.ui.insertdata

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.castprogramms.karma.R
import com.castprogramms.karma.data.Result
import com.castprogramms.karma.databinding.FragmentInsertDataBinding
import com.castprogramms.karma.tools.User
import com.google.android.material.snackbar.Snackbar
import com.google.i18n.phonenumbers.PhoneNumberUtil
import org.koin.android.ext.android.inject
import java.util.regex.Pattern

class InsertDataFragment: Fragment(R.layout.fragment_insert_data) {
    private val insertDataViewModel : InsertDataViewModel by inject()
    lateinit var binding: FragmentInsertDataBinding
    val listCheck = mutableListOf<Boolean>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentInsertDataBinding.bind(view)
        addTextWatchers()
        binding.nameContainer.setHelperTextColor(ColorStateList.valueOf(Color.rgb(139, 128, 249)))
        binding.familyContainer.setHelperTextColor(ColorStateList.valueOf(Color.rgb(139, 128, 249)))
        binding.numberContainer.setHelperTextColor(ColorStateList.valueOf(Color.rgb(139, 128, 249)))
        binding.emailContainer.setHelperTextColor(ColorStateList.valueOf(Color.rgb(139, 128, 249)))
        binding.passwordContainer.setHelperTextColor(ColorStateList.valueOf(Color.rgb(139, 128, 249)))
        binding.passwordRepeatContainer.setHelperTextColor(ColorStateList.valueOf(Color.rgb(139, 128, 249)))
        binding.registration.setOnClickListener {
            validation()
            if (!listCheck.contains(false)) {
                insertDataViewModel.addUser(User(
                    binding.name.text.toString(),
                    binding.family.text.toString(),
                    binding.number.text.toString()
                ), binding.email.text.toString(), binding.password.text.toString())
                insertDataViewModel.getUser().observe(viewLifecycleOwner, {
                    when(it){
                        is Result.Enter -> {}
                        is Result.Auth ->{
                            findNavController().navigate(R.id.action_insertDataFragment_to_loginFragment)
                            binding.progressBar.visibility = View.GONE
                        }
                        is Result.Fail -> {
                            val snackbar = Snackbar.make(view, it.message.toString(), Snackbar.LENGTH_LONG)
                            snackbar.setAction("Закрыть") {
                                snackbar.dismiss()
                                }
                            snackbar.show()
                            binding.progressBar.visibility = View.GONE
                        }
                        is Result.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                    }
                })
            }
        }
    }

    private fun validation(){
        listCheck.clear()
        checkName()
        checkFamily()
        checkEMail()
        checkNumber()
        checkPassword()
        checkPasswordRepeat()
    }

    private fun checkName(){
        if (binding.name.text.isNullOrBlank()) {
            binding.nameContainer.helperText = getString(R.string.wrong_name)
            listCheck.add(false)
        }
    }

    private fun checkFamily(){
        if (binding.family.text.isNullOrBlank()) {
            binding.familyContainer.helperText = getString(R.string.wrong_family)
            listCheck.add(false)
        }
    }

    private fun checkEMail() {
        if (binding.email.text.isNullOrBlank() || !Patterns.EMAIL_ADDRESS.matcher(binding.email.text.toString()).matches()) {
            binding.emailContainer.helperText = getString(R.string.wrong_email)
            listCheck.add(false)
        }
    }

    private fun checkNumber(){
        if (binding.number.text.isNullOrBlank() ||
            (binding.number.text?.length != 11 || (binding.number.text?.first() == '+' && binding.number.text?.length != 12))){
            binding.numberContainer.helperText = getString(R.string.wrong_number_size)
            listCheck.add(false)
        }
        else{
            val phoneUtil = PhoneNumberUtil.getInstance()
            val number = phoneUtil.parse(binding.number.text.toString(), "RU")
            if (!phoneUtil.isValidNumber(number)){
                binding.numberContainer.helperText = getString(R.string.wrong_number)
                listCheck.add(false)
            }
        }
    }

    private fun checkPassword(){
        if (binding.password.text.isNullOrBlank() || binding.password.text?.length!! <= 5) {
            binding.passwordContainer.helperText = getString(R.string.invalid_password)
            listCheck.add(false)
        }
    }

    private fun checkPasswordRepeat(){
        if (binding.password.text?.length!! <= 5){
            binding.passwordRepeatContainer.helperText = getString(R.string.invalid_password)
        }
        else {
            if (binding.passwordRepeat.text.isNullOrBlank() || binding.passwordRepeat.text.toString() != binding.password.text.toString()) {
                binding.passwordRepeatContainer.helperText = getString(R.string.not_equals)
                binding.passwordContainer.helperText = getString(R.string.not_equals)
                listCheck.add(false)
            }
        }
    }

    private fun addTextWatchers(){
        binding.name.addTextChangedListener {
            binding.nameContainer.helperText = ""
        }

        binding.family.addTextChangedListener {
            binding.familyContainer.helperText = ""
        }

        binding.number.addTextChangedListener {
            binding.numberContainer.helperText = ""
        }

        binding.email.addTextChangedListener {
            binding.emailContainer.helperText = ""
        }

        binding.password.addTextChangedListener {
            binding.passwordContainer.helperText = ""
        }

        binding.passwordRepeat.addTextChangedListener {
            binding.passwordRepeatContainer.helperText = ""
        }
    }
}