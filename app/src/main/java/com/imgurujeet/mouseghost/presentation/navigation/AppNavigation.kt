package com.imgurujeet.mouseghost.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.imgurujeet.mouseghost.presentation.screens.FAQScreen
import com.imgurujeet.mouseghost.presentation.screens.HomeScreen
import com.imgurujeet.mouseghost.presentation.screens.OnBoardingScreen
import com.imgurujeet.mouseghost.presentation.screens.SettingScreen


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.OnBoarding.route) {

        // ONBOARDING SCREEN
        composable(Screen.OnBoarding.route) {
            OnBoardingScreen(navController
            )
        }

        // HOME SCREEN
        composable(Screen.Home.route) {
            HomeScreen(
                navController
            )
        }

        // SETTINGS SCREEN
        composable(Screen.Settings.route) {
            SettingScreen(
               navController
            )
        }

        //FAQ SCREEN
        composable(Screen.FAQ.route){
            FAQScreen(navController)
        }
    }
}



sealed class Screen(val route: String) {
    object OnBoarding : Screen("onboarding")
    object Home : Screen("home")
    object Settings : Screen("settings")
    object FAQ : Screen("faq")
}