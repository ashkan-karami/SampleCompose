package com.ashkan.samplecompose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.ashkan.samplecompose.ui.screen.home.HomeRoute
import com.ashkan.samplecompose.ui.screen.login.LoginRoute
import com.ashkan.samplecompose.ui.screen.splash.SplashRoute

@Composable
fun MainNavHost(
    navController: NavHostController
) {

    val onBackPressed = { navController.navigateUp() }
    fun navigateToHome(navOptions: NavOptions) = navController.navigate(Screen.HomeScreen.route, navOptions)
    val backToHome = {
        navController.navigate(Screen.HomeScreen.route) {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
            }
        }
    }

    NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {

        composable(route = Screen.SplashScreen.route) {
            SplashRoute(
                onNavigateToLogin = {
                    navController.navigate(Screen.LoginScreen.route) {
                        launchSingleTop = true
                        popUpTo(Screen.SplashScreen.route) {
                            inclusive = true
                        }
                    }
                },
                onNavigateToHome = {
                    val options = navOptions {
                        launchSingleTop = true
                        popUpTo(Screen.SplashScreen.route) {
                            inclusive = true
                        }
                    }
                    navigateToHome(navOptions = options)
                }
            )
        }
        composable(route = Screen.LoginScreen.route){
            LoginRoute(
                onNavigateToHome = {
                    val options = navOptions {
                        launchSingleTop = true
                        popUpTo(Screen.LoginScreen.route) {
                            inclusive = true
                        }
                    }
                    navigateToHome(navOptions = options)
                }
            )
        }

        composable(route = Screen.HomeScreen.route) {
            HomeRoute()
        }
    }
}