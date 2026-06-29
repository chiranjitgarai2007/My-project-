package com.example.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.ui.screens.*
import com.example.ui.viewmodel.MainViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: MainViewModel = viewModel()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        // SPLASH SCREEN
        composable(Screen.Splash.route) {
            SplashScreen(viewModel = viewModel) { isLoggedIn, isProfileCompleted ->
                if (isLoggedIn) {
                    if (isProfileCompleted) {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                    } else {
                        navController.navigate(Screen.ProfileSetup.route) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                    }
                } else {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            }
        }

        // LOGIN SCREEN
        composable(Screen.Login.route) {
            LoginScreen(viewModel = viewModel) { isProfileCompleted ->
                if (isProfileCompleted) {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                } else {
                    navController.navigate(Screen.ProfileSetup.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            }
        }

        // PROFILE SETUP FLOW
        composable(Screen.ProfileSetup.route) {
            ProfileSetupScreen(viewModel = viewModel) {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.ProfileSetup.route) { inclusive = true }
                }
            }
        }

        // HOME MAIN SCREEN
        composable(Screen.Home.route) {
            HomeScreen(
                viewModel = viewModel,
                onNavigateToComingSoon = { featureName ->
                    navController.navigate(Screen.ComingSoon.createRoute(featureName))
                },
                onLogoutSuccess = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }

        // COMING SOON PLACEHOLDERS
        composable(
            route = Screen.ComingSoon.route,
            arguments = listOf(navArgument("featureName") { type = NavType.StringType })
        ) { backStackEntry ->
            val featureName = backStackEntry.arguments?.getString("featureName") ?: "Feature"
            ComingSoonScreen(
                featureName = featureName,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
