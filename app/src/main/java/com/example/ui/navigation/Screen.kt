package com.example.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object ProfileSetup : Screen("profile_setup")
    object Home : Screen("home")
    object ComingSoon : Screen("coming_soon/{featureName}") {
        fun createRoute(featureName: String) = "coming_soon/$featureName"
    }
}
