package com.ar.moviesapp.presentation.screens.more

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.cash.paging.compose.collectAsLazyPagingItems
import com.ar.moviesapp.core.base.BaseUiState
import com.ar.moviesapp.core.components.EmptyScreen
import com.ar.moviesapp.core.components.ErrorScreen
import com.ar.moviesapp.core.components.LoadingScreen
import com.ar.moviesapp.core.utils.cast
import com.ar.moviesapp.core.utils.getPaddingWithoutTop
import com.ar.moviesapp.presentation.components.CommonPagingList
import com.ar.moviesapp.presentation.components.MovieCard
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MoreMovieScreen(
    paddingValues: PaddingValues
) {
    val viewModel = koinViewModel<MoreMovieViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()


    LaunchedEffect(Unit){
        viewModel.onTriggerEvent(MoreMovieScreenEvent.FetchMoreMovies(castId = 121212))
    }

    when(uiState) {
        is BaseUiState.Data -> {
            val data = uiState.cast<BaseUiState.Data<MoreMovieScreenUiState>>().data
            MoreMovieScreenContent(
                paddingValues = paddingValues,
                uiState = data,
            )
        }
        BaseUiState.Empty -> EmptyScreen(Modifier.padding(paddingValues.getPaddingWithoutTop()))
        is BaseUiState.Error -> {
            val error = uiState.cast<BaseUiState.Error>().error
            ErrorScreen(Modifier.padding(paddingValues.getPaddingWithoutTop()), error)
        }
        BaseUiState.Loading -> LoadingScreen(Modifier.padding(paddingValues.getPaddingWithoutTop()))
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MoreMovieScreenContent(
    paddingValues: PaddingValues,
    uiState: MoreMovieScreenUiState
) {
    val movies = uiState.movies.collectAsLazyPagingItems()
    FlowRow(
        modifier = Modifier.fillMaxSize(),
        maxItemsInEachRow = 3
    ) {
        CommonPagingList(
            modifier = Modifier,
            data = movies
        ){ movie, modifier ->
            movie?.let {
                MovieCard(
                    modifier = modifier,
                    data = it
                )
            }
        }
    }
}