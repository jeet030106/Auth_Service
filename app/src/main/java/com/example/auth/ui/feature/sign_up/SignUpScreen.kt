package com.example.auth.ui.feature.sign_up

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    onNavigateToLogin: () -> Unit
) {

    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val username by viewModel.username.collectAsState()
    val signUpState by viewModel.signUpState.collectAsState()

    val purpleGradient = Brush.horizontalGradient(
        colors = listOf(Color(0xFF9D50FF), Color(0xFFE22DFF))
    )

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Box(
            modifier = Modifier.size(80.dp).background(purpleGradient, RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Star, null, tint = Color.White, modifier = Modifier.size(40.dp))
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text("Create Account", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
        Text("Join us and start your journey", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)

        Spacer(modifier = Modifier.height(32.dp))


        CustomTextField(
            value = username,
            onValueChange = { viewModel.username.value = it },
            label = "Full Name",
            placeholder = "Enter your name",
            leadingIcon = Icons.Default.Person
        )

        Spacer(modifier = Modifier.height(16.dp))


        CustomTextField(
            value = email,
            onValueChange = { viewModel.email.value = it },
            label = "Email",
            placeholder = "Enter your email",
            leadingIcon = Icons.Default.Email
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            value = password,
            onValueChange = { viewModel.password.value = it },
            label = "Password",
            placeholder = "Create a password",
            leadingIcon = Icons.Default.Lock,
            isPassword = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        when (val state = signUpState) {
            is SignUpState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color(0xFF9D50FF))
                }
            }
            else -> {

                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    if (state is SignUpState.Error) {
                        Text(
                            text = state.message,
                            color = Color.Red,
                            modifier = Modifier.padding(bottom = 16.dp),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Button(
                        onClick = { viewModel.signUp { onNavigateToLogin() } },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        contentPadding = PaddingValues(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(purpleGradient),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("Sign Up", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("→", fontSize = 20.sp)
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
            Text("Already have an account? ", color = Color.Gray)
            TextButton(onClick = onNavigateToLogin) {
                Text("Log in", color = Color(0xFF7B1FA2), fontWeight = FontWeight.Bold)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    leadingIcon: androidx.compose.ui.graphics.vector.ImageVector,
    isPassword: Boolean = false
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 8.dp),
            color = Color(0xFF1A1C1E)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = Color.Gray) },
            leadingIcon = { Icon(leadingIcon, contentDescription = null, tint = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            visualTransformation = if (isPassword) PasswordVisualTransformation() else androidx.compose.ui.text.input.VisualTransformation.None,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFF8F9FE),
                unfocusedContainerColor = Color(0xFFF8F9FE),

                focusedIndicatorColor = Color(0xFF9D50FF),
                unfocusedIndicatorColor = Color(0xFFE0E0E0),


                cursorColor = Color(0xFF9D50FF),

                disabledIndicatorColor = Color.Transparent,
            )
        )
    }
}