package com.ar.moviesapp.presentation.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ar.moviesapp.core.components.Colors
import com.ar.moviesapp.core.utils.getSplashPadding
import kotlinx.coroutines.delay
import movies.composeapp.generated.resources.Res
import movies.composeapp.generated.resources.splash
import org.jetbrains.compose.resources.painterResource

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    onNavigateToHome: () -> Unit,
    paddingValues: PaddingValues
) {
    LaunchedEffect(Unit) {
        delay(4000)
        onNavigateToHome()
    }
    Box(
        modifier = modifier.fillMaxSize().padding(paddingValues.getSplashPadding()).background(color = Colors.backGround),
        contentAlignment = Alignment.Center
    ) {
        Image(painter = painterResource(Res.drawable.splash), contentDescription = null)
    }
}