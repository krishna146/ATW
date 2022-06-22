package com.apnatuitionwale.atw.repository

import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import com.apnatuitionwale.atw.R
import com.apnatuitionwale.atw.models.StudentData
import com.apnatuitionwale.atw.ui.LoginFragmentDirections
import com.apnatuitionwale.atw.utils.Constants
import com.apnatuitionwale.atw.utils.UiState
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val database: FirebaseFirestore
) : AuthRepository {
    override fun getOtp(
        phoneNumber: String,
        fragmentActivity: FragmentActivity,
        result: (UiState<String>) -> Unit
    ) {
        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {

            }

            override fun onVerificationFailed(e: FirebaseException) {
                result(UiState.Failure(e.toString()))


            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                result(UiState.Success(verificationId))
            }
        }
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(fragmentActivity)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    override fun verifyOtp(
        systemOtp: String,
        userEnteredOtp: String,
        result: (UiState<Boolean>) -> Unit
    ) {
        val credential = PhoneAuthProvider.getCredential(systemOtp, userEnteredOtp)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(Constants.TAG, "signInWithCredential:success")
                    var isNewUser: Boolean = task.result.additionalUserInfo?.isNewUser == true
                    result(UiState.Success(isNewUser))

                    val user = task.result?.user
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w(Constants.TAG, "signInWithCredential:failure", task.exception)
                    result(UiState.Failure("failure"))
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }

    override fun registerUser(sData: StudentData, result: (UiState<String>) -> Unit) {
        val currentUserId = auth.currentUser!!.uid
        sData.studentId = currentUserId
        database.collection(Constants.FireStoreTable).document(currentUserId).set(sData)
            .addOnSuccessListener {
                result(UiState.Success("User has been Registered Successfully"))
            }
            .addOnFailureListener {
                result(UiState.Failure(it.localizedMessage.toString()))
            }
    }
}