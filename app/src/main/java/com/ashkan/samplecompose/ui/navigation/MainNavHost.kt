package com.ashkan.samplecompose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ashkan.samplecompose.ui.screen.splash.SplashRoute

@Composable
fun MainNavHost(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {
        composable(route = Screen.SplashScreen.route) {
            SplashRoute(
                onNavigateToLogin = { /** TODO navController::navigateToPhone**/ },
                onNavigateToHome = { /** TODO navController::navigateToHome **/ }
            )
        }
    }
}