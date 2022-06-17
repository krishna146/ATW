package com.apnatuitionwale.atw.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.apnatuitionwale.atw.databinding.FragmentOtpVerifyBinding
import com.apnatuitionwale.atw.utils.Constants.TAG
import com.apnatuitionwale.atw.utils.UiState
import com.apnatuitionwale.atw.viewmodel.AuthViewModel
import com.google.firebase.auth.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OtpVerifyFragment : Fragment() {
    private var _binding: FragmentOtpVerifyBinding? = null
    private val binding: FragmentOtpVerifyBinding
        get() = _binding!!
    private val args: OtpVerifyFragmentArgs by navArgs()
    private lateinit var systemOtp: String
    private val viewModel by viewModels<AuthViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOtpVerifyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        systemOtp = args.mobileNumber
        binding.button2.setOnClickListener {
            val userEnteredOtp = with(binding) {
                editTextNumber2.text.toString() + editTextNumber3.text.toString() + editTextNumber4.text.toString() + editTextNumber5.text.toString() + editTextNumber6.text.toString() + editTextNumber7.text.toString()
            }
            viewModel.verifyOtp(systemOtp, userEnteredOtp)
            viewModel.otpVerify.observe(viewLifecycleOwner) { state ->
                when (state) {
                    is UiState.Failure -> Log.d(TAG, "failde")
                    is UiState.Loading -> Log.d(TAG, "Loading")
                    is UiState.Success -> {
                        val action =
                            OtpVerifyFragmentDirections.actionOtpVerifyFragmentToMainFragment2()
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