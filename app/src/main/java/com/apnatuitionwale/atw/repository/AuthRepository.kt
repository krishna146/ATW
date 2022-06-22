package com.apnatuitionwale.atw.repository

import androidx.fragment.app.FragmentActivity
import com.apnatuitionwale.atw.models.StudentData
import com.apnatuitionwale.atw.utils.UiState

interface AuthRepository {
    fun getOtp(
        phoneNumber: String,
        fragmentActivity: FragmentActivity,
        result: (UiState<String>) -> Unit
    )
    fun verifyOtp(
        systemOtp: String,
        userEnteredOtp: String,
        result: (UiState<Boolean>) -> Unit
    )
    fun registerUser(sData: StudentData, result: (UiState<String>) -> Unit)
}