package com.example.auth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.auth.ui.feature.home.HomeScreen
import com.example.auth.ui.feature.navigation.NavRoutes
import com.example.auth.ui.feature.sign_in.LoginScreen
import com.example.auth.ui.feature.sign_up.SignUpScreen
import com.example.auth.ui.theme.AuthTheme
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check Firebase Initialization
        val isFirebaseWorking = FirebaseAuth.getInstance().app != null

        enableEdgeToEdge()

        setContent {
            AuthTheme {
                if (!isFirebaseWorking) {

                    FirebaseStatusScreen(isWorking = false)
                } else {
                    // Initialize NavController
                    val navController = rememberNavController()

                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        Box(modifier = Modifier.padding(innerPadding)) {
                            NavHost(
                                navController = navController,
                                startDestination = NavRoutes.SignUp
                            ) {
                                composable<NavRoutes.SignUp> {
                                    SignUpScreen(
                                        onNavigateToLogin = {
                                            navController.navigate(NavRoutes.SignIn)
                                        }
                                    )
                                }


                                composable<NavRoutes.SignIn> {
                                    LoginScreen(
                                        onNavigateToSignUp ={navController.navigate(NavRoutes.SignUp)},
                                        onLoginSuccess = {
                                            navController.navigate(NavRoutes.Home)
                                        }
                                    )
                                }

                                composable<NavRoutes.Home> {
                                    HomeScreen(
                                        onLogout = {
                                            FirebaseAuth.getInstance().signOut()
                                            navController.navigate(NavRoutes.SignIn)
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FirebaseStatusScreen(isWorking: Boolean) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (isWorking) "🔥 Firebase is connected!" else "❌ Firebase connection failed"
        )
    }
}