package com.castprogramms.karma.ui.login

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.lifecycle.Observer
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.lifecycle.observe
import com.castprogramms.karma.MainActivity
import com.castprogramms.karma.R
import com.castprogramms.karma.data.Result
import com.castprogramms.karma.databinding.FragmentLoginBinding
import org.koin.android.viewmodel.ext.android.viewModel

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val loginViewModel: LoginViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentLoginBinding.bind(view)
        binding.usernameContainer.setHelperTextColor(ColorStateList.valueOf(Color.rgb(139, 128, 249)))
        binding.passwordContainer.setHelperTextColor(ColorStateList.valueOf(Color.rgb(139, 128, 249)))

        loginViewModel.loginFormState.observe(viewLifecycleOwner,
            Observer { loginFormState ->
                if (loginFormState == null) {
                    return@Observer
                }
                binding.login.isEnabled = loginFormState.isDataValid
                loginFormState.usernameError?.let {
                    binding.usernameContainer.helperText = getString(it)
                }
                loginFormState.passwordError?.let {
                    binding.passwordContainer.helperText = getString(it)
                }
            })

        val afterTextChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                loginViewModel.loginDataChanged(
                    binding.username.text.toString(),
                    binding.password.text.toString()
                )
            }
        }
        binding.username.addTextChangedListener(afterTextChangedListener)
        binding.password.addTextChangedListener(afterTextChangedListener)

        binding.login.setOnClickListener {
            loginViewModel.login(
                binding.username.text.toString(),
                binding.password.text.toString()
            )
        }

        loginViewModel.user.observe(viewLifecycleOwner) {
            if (it != null) {
                when (it) {
                    is Result.Loading ->{
                        binding.loading.visibility = View.VISIBLE
                    }
                    is Result.Auth -> {}
                    is Result.Enter -> {
                        binding.loading.visibility = View.GONE
                        updateUiWithUser(LoggedInUserView(it.data?.providerId!!, it.data.uid, false))
                    }
                    is Result.Fail -> {
                        binding.loading.visibility = View.GONE
                        showLoginFailed(it.message.toString())
                    }
                }
            }
        }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome) + model.id
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, welcome, Toast.LENGTH_LONG).show()
        startActivity(Intent(requireActivity(), MainActivity::class.java))
        requireActivity().finish()
    }

    private fun showLoginFailed(errorString: String) {
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext,
            errorString
            , Toast.LENGTH_LONG).show()
    }
}