package com.ar.moviesapp.presentation.screens.watchlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ar.moviesapp.core.base.BaseUiState
import com.ar.moviesapp.core.components.Colors.backGround
import com.ar.moviesapp.core.components.Colors.onBackGround
import com.ar.moviesapp.core.components.EmptyScreen
import com.ar.moviesapp.core.components.ErrorScreen
import com.ar.moviesapp.core.components.LoadingScreen
import com.ar.moviesapp.core.utils.ScreenSize
import com.ar.moviesapp.core.utils.cast
import com.ar.moviesapp.core.utils.getPaddingWithoutTop
import com.ar.moviesapp.presentation.components.WatchListMovieCard
import movies.composeapp.generated.resources.Res
import movies.composeapp.generated.resources.ic_back
import movies.composeapp.generated.resources.watchlist_empty
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun WatchListScreen(
    paddingValues: PaddingValues,
    goToDetails: (Int) -> Unit,
) {
    val viewModel = koinViewModel<WatchlistViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit){
        viewModel.onTriggerEvent(WatchlistScreenEvent.FetchWatchList)
    }

    when(uiState){
        is BaseUiState.Data -> {
            val data = uiState.cast<BaseUiState.Data<WatchlistScreenUiState>>().data
            WatchListScreenContent(
                paddingValues = paddingValues,
                uiState = data,
                goToDetails = { goToDetails.invoke(it) }
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

@Composable
fun WatchListScreenContent(
    paddingValues: PaddingValues,
    uiState: WatchlistScreenUiState,
    goToDetails: (Int) -> Unit = {},
){
    val screen = koinInject<ScreenSize>()

    Column(
        modifier = Modifier.fillMaxSize().padding(paddingValues.getPaddingWithoutTop())
            .background(backGround),
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().height(50.sdp)
                .padding(top = paddingValues.calculateTopPadding() + 7.sdp)
                .padding(horizontal = 12.sdp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = vectorResource(Res.drawable.ic_back),
                contentDescription = "Back",
                tint = Color.Transparent
            )
            Text(
                modifier = Modifier.weight(1f),
                text = "WatchList",
                color = onBackGround,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 14.ssp,
                    textAlign = TextAlign.Center
                )
            )
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = "Info",
                tint = Color.Transparent
            )
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(14.sdp, Alignment.Top),
            contentPadding = PaddingValues(bottom = 24.sdp, top = 24.sdp)
        ){
            itemsIndexed(uiState.watchList) { _, movie ->
                WatchListMovieCard(
                    modifier = Modifier,
                    movie = movie
                ) {
                    goToDetails.invoke(it.id)
                }
            }
            if(uiState.watchList.isEmpty()){
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .heightIn(min = (screen.getHeight()/1.6).dp, max = screen.getHeight().dp),
                        contentAlignment = Alignment.Center
                    ){
                        Image(painter = painterResource(Res.drawable.watchlist_empty), contentDescription = null)
                    }
                }
            }
        }
    }
}