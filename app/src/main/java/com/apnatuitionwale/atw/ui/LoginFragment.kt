package com.apnatuitionwale.atw.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import com.apnatuitionwale.atw.R
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.apnatuitionwale.atw.databinding.FragmentLoginBinding
import com.apnatuitionwale.atw.utils.Constants.TAG
import com.apnatuitionwale.atw.utils.UiState
import com.apnatuitionwale.atw.utils.toast
import com.apnatuitionwale.atw.viewmodel.AuthViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding
        get() = _binding!!
    private val viewModel by viewModels<AuthViewModel>()

    @Inject
    lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener {
            if (validation()) {
                val phoneNumber = "+91" + binding.etPhone.text.toString().trim()
                viewModel.getOtp(phoneNumber, requireActivity())
                observer()
            }

        }
        binding.etPhone.doAfterTextChanged {
            binding.textFieldPhone.error = null
        }
    }

    private fun validation(): Boolean {
        val phone = binding.etPhone.text.toString()
        if (phone.isEmpty()) {
            binding.textFieldPhone.error = "Enter Mobile Number"
            return false
        }
        if (phone.length < 10 || phone.length > 10) {
            binding.textFieldPhone.error = getString(R.string.error)
            return false
        }
        return true
    }

    private fun observer() {
        viewModel.login.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Failure -> {
                    toast(state.error!!)
                    binding.loginProgressBar.isVisible = false
                }

                is UiState.Loading -> {
                    binding.loginProgressBar.isVisible = true
                }
                is UiState.Success -> {
                    val action =
                        LoginFragmentDirections.actionLoginFragmentToOtpVerifyFragment(state.data)
                    findNavController().navigate(action)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }


}