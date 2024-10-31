package com.ar.moviesapp.presentation.screens.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ar.moviesapp.core.components.Colors.backGround
import com.ar.moviesapp.core.components.Colors.onSearchContainer
import com.ar.moviesapp.core.components.Colors.stroke
import com.ar.moviesapp.core.components.ContentScreen
import com.ar.moviesapp.presentation.navigation.AppNavigation
import com.ar.moviesapp.presentation.navigation.Home
import com.ar.moviesapp.presentation.navigation.Search
import com.ar.moviesapp.presentation.navigation.WatchList
import kotlinx.serialization.Serializable
import movies.composeapp.generated.resources.Res
import movies.composeapp.generated.resources.ic_home
import movies.composeapp.generated.resources.ic_search
import movies.composeapp.generated.resources.ic_watchlist
import network.chaintech.sdpcomposemultiplatform.sdp
import org.jetbrains.compose.resources.painterResource


@Serializable
sealed class BottomScreen<T>(
    val title: String,
    val route: T,
) {
    @Serializable
    data object BottomHome : BottomScreen<Home>("Home", Home)

    @Serializable
    data object BottomSearch : BottomScreen<Search>("Search", Search)

    @Serializable
    data object BottomWatchList : BottomScreen<WatchList>("Watchlist", WatchList)
}


@Composable
fun MainScreen() {

    val navItemList = listOf(
        BottomScreen.BottomHome,
        BottomScreen.BottomSearch,
        BottomScreen.BottomWatchList
    )

    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute by remember(backStackEntry) {
        derivedStateOf {
            backStackEntry?.destination
        }
    }
    val bottomBarVisibility by remember(backStackEntry) {
        derivedStateOf {
            navItemList.any {
                it.route::class.qualifiedName == currentRoute?.route
            }
        }
    }
    println(bottomBarVisibility)
    ContentScreen(
        bottomBar = {
            AnimatedVisibility(
                modifier = Modifier.background(backGround),
                visible = bottomBarVisibility,
                enter = slideInVertically(
                    initialOffsetY = { fullHeight -> fullHeight }
                ),
                exit = slideOutVertically(
                    targetOffsetY = { fullHeight -> fullHeight }
                )
            ) {
                Column {
                    Box(modifier = Modifier.height(1.sdp).fillMaxWidth().background(stroke))
                    NavigationBar(
                        modifier = Modifier.fillMaxWidth(),
                        containerColor = backGround
                    ) {
                        navItemList.forEach { navigationItem ->

                            val isSelected = currentRoute?.hierarchy?.any {
                                it.route == navigationItem.route::class.qualifiedName
                            } == true

                            NavigationBarItem(
                                selected = isSelected,
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = stroke,
                                    selectedTextColor = stroke,
                                    unselectedIconColor = onSearchContainer,
                                    unselectedTextColor = onSearchContainer,
                                    indicatorColor = Color.Transparent
                                ),
                                onClick = {
                                    if (currentRoute?.route != navigationItem.route::class.qualifiedName) {
                                        navController.navigate(navigationItem.route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                },
                                icon = {
                                    Icon(
                                        painter = painterResource(
                                            when (navigationItem.title) {
                                                "Search" -> Res.drawable.ic_search
                                                "Watchlist" -> Res.drawable.ic_watchlist
                                                else -> Res.drawable.ic_home
                                            }
                                        ),
                                        contentDescription = null,
                                    )
                                },
                                label = {
                                    Text(
                                        text = navigationItem.title,
                                        style = if (navigationItem.route == currentRoute) MaterialTheme.typography.labelLarge
                                        else MaterialTheme.typography.labelMedium,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    ) {
        AppNavigation(navController, it)
    }
}