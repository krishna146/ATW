package com.apnatuitionwale.atw.viewmodel

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.apnatuitionwale.atw.repository.AuthRepository
import com.apnatuitionwale.atw.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {
    private val _login = MutableLiveData<UiState<String>>()
    val login: LiveData<UiState<String>>
        get() = _login
    private val _otpVerify = MutableLiveData<UiState<String>>()
    val otpVerify: LiveData<UiState<String>>
        get() = _otpVerify

    fun getOtp(phoneNumber: String, fragmentActivity: FragmentActivity) {
        _login.value = UiState.Loading
        UiState.Loading
        repository.getOtp(phoneNumber, fragmentActivity) {
            _login.value = it
        }
    }

    fun verifyOtp(systemOtp: String, userEnteredOtp: String) {
        _otpVerify.value = UiState.Loading
        repository.verifyOtp(systemOtp, userEnteredOtp) {
            _otpVerify.value = it
        }

    }

}