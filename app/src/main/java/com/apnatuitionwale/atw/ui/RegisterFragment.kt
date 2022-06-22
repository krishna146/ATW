package com.apnatuitionwale.atw.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.apnatuitionwale.atw.R
import com.apnatuitionwale.atw.databinding.FragmentOtpVerifyBinding
import com.apnatuitionwale.atw.databinding.FragmentRegisterBinding
import com.apnatuitionwale.atw.models.StudentData
import com.apnatuitionwale.atw.utils.UiState
import com.apnatuitionwale.atw.utils.toast
import com.apnatuitionwale.atw.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding : FragmentRegisterBinding? = null
    private val binding: FragmentRegisterBinding
        get() = _binding!!
    private val viewModel by viewModels<AuthViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnRegister.setOnClickListener{
            registerUser()
            observer()
        }

    }

    private fun registerUser() {
        //TODO: Validation
        val studentId = ""
        val sName = binding.etSName.text.toString()
        val sClass = binding.etClass.text.toString()
        val sDob = binding.etDob.text.toString()
        val sEmail = binding.etEmail.text.toString()
        val sCity = binding.etCity.text.toString()
        val sState = binding.etState.text.toString()
        val sData = StudentData(studentId, sName, sClass, sDob, sEmail, sCity, sState)
        viewModel.registerUser(sData)
    }

    private fun observer(){
        viewModel.register.observe(viewLifecycleOwner){state ->
            when(state){
                is UiState.Failure -> {
                    binding.progressBar.isVisible = false
                    toast(state.error!!)
                }
                UiState.Loading ->{
                    binding.progressBar.isVisible = true
                }
                is UiState.Success -> {
                    findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}