package com.example.auth.ui.feature.navigation

import kotlinx.serialization.Serializable


@Serializable sealed interface NavRoutes{

    @Serializable
    object SignUp : NavRoutes

    @Serializable
    object SignIn : NavRoutes

    @Serializable
    object Home : NavRoutes
}