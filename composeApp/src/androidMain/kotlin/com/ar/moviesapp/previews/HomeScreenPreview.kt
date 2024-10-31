package com.ar.moviesapp.previews

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ar.moviesapp.presentation.screens.home.HomeScreenContent
import com.ar.moviesapp.presentation.screens.home.HomeScreenUiState

@Preview
@Composable
fun HomePreview() {
    HomeScreenContent(
        PaddingValues(15.dp),
        uiState = HomeScreenUiState(),
        onEvent = {},
        onNavigation = {}
    )
}