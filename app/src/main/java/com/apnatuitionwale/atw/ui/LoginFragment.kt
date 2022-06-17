package com.apnatuitionwale.atw.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import com.apnatuitionwale.atw.R
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.apnatuitionwale.atw.databinding.FragmentLoginBinding
import com.apnatuitionwale.atw.utils.Constants.TAG
import com.apnatuitionwale.atw.utils.UiState
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
    private lateinit var systemOtp: String
    private val viewModel by viewModels<AuthViewModel>()

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
            val phoneNumber = "+91" + binding.etPhone.text.toString().trim()
            viewModel.getOtp(phoneNumber, requireActivity())
            viewModel.login.observe(viewLifecycleOwner) { state ->
                when (state) {
                    is UiState.Failure -> Log.d(TAG, "Failure")
                    is UiState.Loading -> Log.d(TAG, "Loading")
                    is UiState.Success -> {
                        val action = LoginFragmentDirections.actionLoginFragmentToOtpVerifyFragment(state.data)
                        findNavController().navigate(action)
                    }
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }


}