package com.example.auth.ui.feature.sign_up

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {

    val email = MutableStateFlow("")
    val password = MutableStateFlow("")
    val username = MutableStateFlow("")

    private val _signUpState = MutableStateFlow<SignUpState>(SignUpState.Idle)
    val signUpState: StateFlow<SignUpState> = _signUpState.asStateFlow()

    fun signUp(onSuccess: () -> Unit) {
        val emailVal = email.value
        val passVal = password.value
        val userVal = username.value

        if (emailVal.isEmpty() || passVal.isEmpty() || userVal.isEmpty()) {
            _signUpState.value = SignUpState.Error("All fields are required")
            return
        }

        _signUpState.value = SignUpState.Loading

        auth.createUserWithEmailAndPassword(emailVal, passVal)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val profileUpdates = userProfileChangeRequest {
                        displayName = userVal
                    }

                    auth.currentUser?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener { profileTask ->
                            if (profileTask.isSuccessful) {
                                _signUpState.value = SignUpState.Success("Account Created!")
                                onSuccess()
                            } else {
                                _signUpState.value = SignUpState.Error("Failed to set username")
                            }
                        }
                } else {
                    _signUpState.value = SignUpState.Error(task.exception?.message ?: "Sign up failed")
                }
            }
    }
}

sealed class SignUpState {
    object Idle : SignUpState()
    object Loading : SignUpState()
    data class Success(val message: String) : SignUpState()
    data class Error(val message: String) : SignUpState()
}