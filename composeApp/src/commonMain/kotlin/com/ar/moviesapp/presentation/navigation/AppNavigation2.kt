package com.ar.moviesapp.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.ar.moviesapp.presentation.screens.details.DetailsScreen
import com.ar.moviesapp.presentation.screens.home.HomeScreen
import com.ar.moviesapp.presentation.screens.search.SearchScreen
import com.ar.moviesapp.presentation.screens.splash.SplashScreen
import com.ar.moviesapp.presentation.screens.watchlist.WatchListScreen
import kotlinx.serialization.Serializable

@Serializable
data object Splash

@Serializable
data object Main

@Serializable
data object Home

@Serializable
data object Search

@Serializable
data object WatchList

@Serializable
data class Details(val movieId: Int)

@Composable
fun AppNavigation(
    navController: NavHostController,
    paddingValues: PaddingValues,
) {
    NavHost(navController = navController, startDestination = Splash) {
        composable<Splash> {
            SplashScreen(
                paddingValues = paddingValues,
                onNavigateToHome = {
                    navController.navigate(Main)
                }
            )
        }
        mainGraph(navController, paddingValues)
        composable<Details> {
            val route = it.toRoute<Details>()
            DetailsScreen(
                paddingValues = paddingValues,
                goBack = {
                    navController.navigateUp()
                },
                movieId = route.movieId
            )
        }
    }
}

fun NavGraphBuilder.mainGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
) {
    navigation(route = Main::class, startDestination = Home::class) {
        composable<Home> {
            HomeScreen(
                paddingValues = paddingValues,
                onNavigateToDetails = {
                    navController.navigate(Details(movieId = it))
                }
            )
        }
        composable<Search> {
            SearchScreen(
                paddingValues = paddingValues,
                goToDetails = {
                    navController.navigate(Details(movieId = it))
                }
            )
        }
        composable<WatchList> {
            WatchListScreen(
                paddingValues = paddingValues,
                goToDetails = {
                    navController.navigate(Details(movieId = it))
                }
            )
        }
    }
}

