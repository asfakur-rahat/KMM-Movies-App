package com.ar.moviesapp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.ar.moviesapp.presentation.screens.main.MainScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext

@Composable
@Preview
fun App() {
    KoinContext {
        MaterialTheme {
            MainScreen()
        }
    }
}