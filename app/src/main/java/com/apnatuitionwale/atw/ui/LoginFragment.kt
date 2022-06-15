package com.apnatuitionwale.atw.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.apnatuitionwale.atw.R
import com.apnatuitionwale.atw.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private var _binding :FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding
    get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }



}