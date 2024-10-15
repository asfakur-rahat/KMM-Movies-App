package com.ar.moviesapp.presentation.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ar.moviesapp.core.base.BaseUiState
import com.ar.moviesapp.core.components.Colors.backGround
import com.ar.moviesapp.core.components.Colors.onBackGround
import com.ar.moviesapp.core.components.EmptyScreen
import com.ar.moviesapp.core.components.ErrorScreen
import com.ar.moviesapp.core.components.LoadingScreen
import com.ar.moviesapp.core.utils.cast
import com.ar.moviesapp.core.utils.getExtraTopPadding
import com.ar.moviesapp.core.utils.getPaddingWithoutTop
import com.ar.moviesapp.domain.model.Movie
import com.ar.moviesapp.presentation.components.MovieCard
import com.ar.moviesapp.presentation.components.TopMovieCard
import com.ar.moviesapp.presentation.navigation.AppScreen
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    onNavigateToDetails: () -> Unit,
) {
    val viewModel = koinViewModel<HomeViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onTriggerEvent(HomeScreenEvent.FetchMovies)
    }

    when (uiState) {
        is BaseUiState.Data -> {
            val data = uiState.cast<BaseUiState.Data<HomeScreenUiState>>().data
            HomeScreenContent(
                paddingValues = paddingValues,
                uiState = data,
                onEvent = {
                    viewModel.onTriggerEvent(it)
                },
                onNavigation = {

                }
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

enum class Tabs(val text: String) {
    NowPlaying("Now Playing"),
    Upcoming("Upcoming"),
    TopRated("Top Rated"),
    Popular("Popular")
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreenContent(
    paddingValues: PaddingValues,
    uiState: HomeScreenUiState,
    onEvent: (HomeScreenEvent) -> Unit,
    onNavigation: (AppScreen) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { 4 })
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }
    Box(
        modifier = Modifier.fillMaxSize().padding(paddingValues.getPaddingWithoutTop())
            .background(backGround),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = paddingValues.getExtraTopPadding()
        ) {
            item {
                Text(
                    modifier = Modifier.padding(start = 15.dp),
                    text = "What do you want to watch?",
                    color = onBackGround,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.Start)) {
                    itemsIndexed(uiState.topFiveMovie) { index, item ->
                        TopMovieCard(
                            data = item,
                            index = index,
                            onClick = {

                            }
                        )
                    }
                }
            }
            item {
                ScrollableTabRow(
                    containerColor = Color.Transparent,
                    contentColor = onBackGround,
                    selectedTabIndex = selectedTabIndex.value,
                    divider = {},
                    edgePadding = 15.dp,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex.value]),
                            color = onBackGround
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Tabs.entries.forEachIndexed { index, currentTab ->
                        Tab(
                            selected = selectedTabIndex.value == index,
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(currentTab.ordinal)
                                }
                            },
                            text = { Text(text = currentTab.text) }
                        )
                    }
                }

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                ) {
                    when(Tabs.entries[selectedTabIndex.value].text){
                        "Upcoming" ->{
                            UpcomingMovies(Modifier, uiState.topFiveMovie)
                        }
                        "Top Rated" ->{
                            TopRatedMovies(Modifier, uiState.topRatedMovie)
                        }
                        "Popular" -> {
                            PopularMovies(Modifier, uiState.topFiveMovie)
                        }
                        else ->{
                            NowPlayingMovies(Modifier, uiState.topRatedMovie)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NowPlayingMovies(
    modifier: Modifier,
    movies: List<Movie> = emptyList(),
    onclick: (Movie) -> Unit = {}
) {
    FlowRow(
        modifier = modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        maxItemsInEachRow = 3
    ){
        movies.forEachIndexed { index, movie ->
            MovieCard(data = movie, index = index){
                onclick.invoke(it)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun UpcomingMovies(
    modifier: Modifier,
    movies: List<Movie> = emptyList(),
    onclick: (Movie) -> Unit = {}
) {
    FlowRow(
        modifier = modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        maxItemsInEachRow = 3
    ){
        movies.forEachIndexed { index, movie ->
            MovieCard(data = movie, index = index){
                onclick.invoke(it)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TopRatedMovies(
    modifier: Modifier,
    movies: List<Movie> = emptyList(),
    onclick: (Movie) -> Unit = {}
) {
    FlowRow(
        modifier = modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        maxItemsInEachRow = 3
    ){
        movies.forEachIndexed { index, movie ->
            MovieCard(data = movie, index = index){
                onclick.invoke(it)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PopularMovies(
    modifier: Modifier,
    movies: List<Movie> = emptyList(),
    onclick: (Movie) -> Unit = {}
) {
    FlowRow(
        modifier = modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        maxItemsInEachRow = 3
    ){
        movies.forEachIndexed { index, movie ->
            MovieCard(data = movie, index = index){
                onclick.invoke(it)
            }
        }
    }
}