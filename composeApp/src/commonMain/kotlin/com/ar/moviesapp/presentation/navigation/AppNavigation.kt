package com.ar.moviesapp.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.ar.moviesapp.presentation.screens.home.HomeScreen
import com.ar.moviesapp.presentation.screens.splash.SplashScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    paddingValues: PaddingValues,
) {
    NavHost(navController = navController, startDestination = AppScreen.Splash.route) {
        composable(route = AppScreen.Splash.route) {
            SplashScreen(
                paddingValues = paddingValues,
                onNavigateToHome = {
                    navController.navigate(AppScreen.Main.route)
                }
            )
        }
        mainGraph(navController, paddingValues)
        composable(route = AppScreen.Details.route) {

        }
    }
}

fun NavGraphBuilder.mainGraph(navController: NavHostController, paddingValues: PaddingValues) {
    navigation(route = AppScreen.Main.route, startDestination = AppScreen.Home.route) {
        composable(route = AppScreen.Home.route) {
            HomeScreen(paddingValues){

            }
        }
        composable(route = AppScreen.Search.route) {

        }
        composable(route = AppScreen.WatchList.route) {

        }
    }
}


sealed class AppScreen(
    val route: String,
) {
    data object Splash : AppScreen("splash")
    data object Main : AppScreen("main")
    data object Details : AppScreen("details")
    data object Home : AppScreen("main/home")
    data object Search : AppScreen("main/search")
    data object WatchList : AppScreen("main/watchlist")

}