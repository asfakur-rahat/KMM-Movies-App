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
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ar.moviesapp.core.components.Colors
import com.ar.moviesapp.core.components.Colors.onSearchContainer
import com.ar.moviesapp.core.components.Colors.stroke
import com.ar.moviesapp.core.components.ContentScreen
import com.ar.moviesapp.presentation.navigation.AppNavigation
import com.ar.moviesapp.presentation.navigation.AppScreen
import movies.composeapp.generated.resources.Res
import movies.composeapp.generated.resources.ic_home
import movies.composeapp.generated.resources.ic_search
import movies.composeapp.generated.resources.ic_watchlist
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute by remember(backStackEntry) {
        derivedStateOf {
            backStackEntry?.destination?.route
        }
    }
    val bottomBarVisibility by remember(backStackEntry) {
        derivedStateOf {
            currentRoute != AppScreen.Splash.route && currentRoute != AppScreen.Details.route
        }
    }
    println(bottomBarVisibility)
    ContentScreen(
        bottomBar = {
            AnimatedVisibility(
                visible = bottomBarVisibility,
                enter = slideInVertically(
                    initialOffsetY = { fullHeight -> fullHeight }
                ),
                exit = slideOutVertically(
                    targetOffsetY = { fullHeight -> fullHeight }
                )
            ){
                BottomNavigation(navItemList, currentRoute){
                    if(it.route != currentRoute){
                        navController.navigate(it.route){
                            navController.graph.startDestinationRoute?.let { route->
                                popUpTo(route){
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            }
        }
    ){
        AppNavigation(navController, it)
    }
}

private val navItemList = listOf(
    NavigationItem(icon = Res.drawable.ic_home, title = "home", route = AppScreen.Home.route),
    NavigationItem(icon = Res.drawable.ic_search, title = "Search", route = AppScreen.Search.route),
    NavigationItem(icon = Res.drawable.ic_watchlist, title = "Watchlist", route = AppScreen.WatchList.route),
)

data class NavigationItem(
    val icon: DrawableResource,
    val title: String,
    val route: String,
)

@Composable
fun BottomNavigation(
    items: List<NavigationItem>,
    currentRoute: String?,
    onItemClick: (NavigationItem) -> Unit,
) {
    Column{
        Box(modifier = Modifier.height(1.dp).fillMaxWidth().background(stroke))
        NavigationBar(
            modifier = Modifier.fillMaxWidth(),
            containerColor = Colors.backGround
        ) {
            items.forEach { navigationItem ->
                NavigationBarItem(
                    selected = currentRoute == navigationItem.route,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = stroke,
                        selectedTextColor = stroke,
                        unselectedIconColor = onSearchContainer,
                        unselectedTextColor = onSearchContainer,
                        indicatorColor = Color.Transparent
                    ),
                    onClick = { onItemClick(navigationItem) },
                    icon = {
                        Icon(
                            painter = painterResource(navigationItem.icon),
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
                    },
                )
            }
        }
    }
}