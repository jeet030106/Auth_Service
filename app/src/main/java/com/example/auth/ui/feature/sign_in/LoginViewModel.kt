package com.example.auth.ui.feature.sign_in

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {

    val email = MutableStateFlow("")
    val password = MutableStateFlow("")

    // UI State using Sealed Class
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    fun login(onSuccess: () -> Unit) {
        val emailVal = email.value
        val passVal = password.value

        if (emailVal.isEmpty() || passVal.isEmpty()) {
            _loginState.value = LoginState.Error("Please fill in all fields")
            return
        }

        _loginState.value = LoginState.Loading

        auth.signInWithEmailAndPassword(emailVal, passVal)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _loginState.value = LoginState.Success
                    onSuccess()
                } else {
                    _loginState.value = LoginState.Error(task.exception?.message ?: "Login failed")
                }
            }
    }

    sealed class LoginState {
        object Idle : LoginState()
        object Loading : LoginState()
        object Success : LoginState()
        data class Error(val message: String) : LoginState()
    }
}