package com.example.sampleapp.presentation.login

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.sampleapp.MainActivity
import com.example.sampleapp.R
import com.example.sampleapp.databinding.FragmentDetailsBinding
import com.example.sampleapp.databinding.FragmentLoginBinding
import com.example.sampleapp.presentation.home.HomeViewModel
import com.example.sampleapp.presentation.splash.SplashActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        setUpViews()

        binding.viewModel = loginViewModel
        return binding.root
    }

    private fun setUpViews() {

        binding.apply {

            emailEt.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    setDetailsText()
                    setLoginButton()
                }

            })

            passwordEt.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    setDetailsText()
                    setLoginButton()
                }

            })


            passwordToggle.setOnClickListener {
                if (passwordEt.transformationMethod.equals(PasswordTransformationMethod.getInstance())) {
                    passwordEt.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    passwordToggle.setImageResource(R.drawable.ic_toggle_true)
                } else {
                    passwordEt.transformationMethod = PasswordTransformationMethod.getInstance()
                    passwordToggle.setImageResource(R.drawable.ic_toggle_false)
                }
                passwordEt.setSelection(passwordEt.length())

            }

            btnNext.setOnClickListener {
                lifecycleScope.launch {
                    (activity as SplashActivity).preferenceStorage.saveUserName(binding.emailEt.text.toString())
                    (activity as SplashActivity).preferenceStorage.saveProfileStatusToDataStore(true)

                    val intent = Intent(requireContext(), MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    (activity as SplashActivity).finish()
                }

            }
        }
    }

    private fun setDetailsText() {
        binding.apply {
            if (!loginViewModel.getEmailLength() && !loginViewModel.getPwdLength()) {
                detailsTv.text =
                    getString(R.string.length_of_password_and_username_must_be_greater_than_5)
            } else if (!loginViewModel.getEmailLength() && loginViewModel.getPwdLength()) {
                detailsTv.text = getString(R.string.length_of_password)
            } else if (loginViewModel.getEmailLength() && !loginViewModel.getPwdLength()) {
                detailsTv.text = getString(R.string.length_of_username)
            }
        }
    }

    private fun setLoginButton() {
        if (binding.emailEt.text.length > 5 && binding.passwordEt.text.length > 5) {
            binding.btnNext.isEnabled = true
            binding.detailsTv.visibility = View.INVISIBLE
            binding.btnNext.alpha = 1f
        } else {
            binding.detailsTv.visibility = View.VISIBLE
            binding.btnNext.isEnabled = false
            binding.btnNext.alpha = 0.4f
        }
    }

}