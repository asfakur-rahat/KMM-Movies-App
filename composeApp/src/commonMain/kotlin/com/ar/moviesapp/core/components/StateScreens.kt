package com.ar.moviesapp.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ar.moviesapp.core.components.Colors.backGround
import com.ar.moviesapp.core.components.Colors.onBackGround

@Composable
fun LoadingScreen(modifier: Modifier) {
    Box(
        modifier = modifier.fillMaxSize().background(backGround),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = onBackGround)
    }
}

@Composable
fun ErrorScreen(modifier: Modifier, error: Throwable) {
    Box(
        modifier = modifier.fillMaxSize().background(backGround),
        contentAlignment = Alignment.Center
    ) {
        println(error.message)
        Text(text = error.message ?: "Unknown Error", color = onBackGround)
    }
}

@Composable
fun EmptyScreen(modifier: Modifier) {
    Box(
        modifier = modifier.fillMaxSize().background(backGround),
        contentAlignment = Alignment.Center
    ) {

    }
}

@Composable
fun ContentScreen(
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = topBar,
        bottomBar = bottomBar
    ){
        content(it)
    }
}


