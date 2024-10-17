package com.ar.moviesapp.presentation.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SearchBarDefaults.inputFieldColors
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
import androidx.compose.ui.text.input.BackspaceCommand
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ar.moviesapp.core.base.BaseUiState
import com.ar.moviesapp.core.components.Colors.backGround
import com.ar.moviesapp.core.components.Colors.onBackGround
import com.ar.moviesapp.core.components.Colors.onSearchContainer
import com.ar.moviesapp.core.components.Colors.searchContainer
import com.ar.moviesapp.core.components.Colors.stroke
import com.ar.moviesapp.core.components.EmptyScreen
import com.ar.moviesapp.core.components.ErrorScreen
import com.ar.moviesapp.core.components.LoadingScreen
import com.ar.moviesapp.core.utils.ScreenSize
import com.ar.moviesapp.core.utils.cast
import com.ar.moviesapp.core.utils.getPaddingWithoutTop
import com.ar.moviesapp.domain.model.Movie
import com.ar.moviesapp.presentation.components.MovieCard
import com.ar.moviesapp.presentation.components.SearchResultCard
import com.ar.moviesapp.presentation.components.TopMovieCard
import kotlinx.coroutines.launch
import movies.composeapp.generated.resources.Res
import movies.composeapp.generated.resources.ic_search
import movies.composeapp.generated.resources.search_empty
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    onNavigateToDetails: (String) -> Unit,
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
                    onNavigateToDetails.invoke(it)
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


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    paddingValues: PaddingValues,
    uiState: HomeScreenUiState,
    onEvent: (HomeScreenEvent) -> Unit,
    onNavigation: (String) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { 4 })
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }

    val screen = koinInject<ScreenSize>()

    Box(
        modifier = Modifier.fillMaxSize().padding(paddingValues.getPaddingWithoutTop())
            .background(backGround),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
                .heightIn(max = 800.sdp)
                .padding(top = paddingValues.calculateTopPadding() + 12.sdp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(14.sdp, Alignment.Top),
            contentPadding = PaddingValues(start = 12.sdp, end = 12.sdp, bottom = 24.sdp)
        ) {
            item {
                Text(
                    modifier = Modifier.padding(start = 12.sdp),
                    text = "What do you want to watch?",
                    color = onBackGround,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 17.ssp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                SearchBar(
                    query = uiState.searchQuery,
                    onQueryChange = {
                        onEvent(HomeScreenEvent.OnSearchQueryChange(it))
                    },
                    onSearch = {
                        onEvent(HomeScreenEvent.OnSearch(it))
                    },
                    onActiveChange = {
                        onEvent(HomeScreenEvent.OnSearchMode(it))
                    },
                    modifier = Modifier.fillMaxWidth().heightIn(
                        max = screen.getHeight().dp
                    ),
                    placeholder = { Text(text = "Search using title") },
                    trailingIcon = {
                        Icon(
                            imageVector = vectorResource(Res.drawable.ic_search),
                            contentDescription = null,
                            tint = onSearchContainer
                        )
                    },
                    active = uiState.searchMode,
                    colors = SearchBarDefaults.colors(
                        containerColor = searchContainer,
                        dividerColor = stroke,
                        inputFieldColors = inputFieldColors(
                            focusedTextColor = onBackGround,
                            unfocusedTextColor = onBackGround,
                            disabledTextColor = onBackGround,
                            focusedPlaceholderColor = onSearchContainer,
                            unfocusedPlaceholderColor = onSearchContainer,
                            disabledPlaceholderColor = onSearchContainer,
                            cursorColor = onSearchContainer,
                        )
                    ),
                    shape = RoundedCornerShape(25)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = screen.getHeight().dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(14.sdp, Alignment.Top),
                        contentPadding = PaddingValues(start = 12.sdp, end = 12.sdp)
                    ) {
                        item {
                            Box(modifier = Modifier.fillMaxWidth().height(40.sdp)) {
                                Icon(
                                    modifier = Modifier
                                        .align(Alignment.CenterEnd)
                                        .padding(end = 8.sdp)
                                        .size(28.sdp)
                                        .clickable {
                                            onEvent(HomeScreenEvent.OnSearchMode(false))
                                        },
                                    imageVector = Icons.Default.Close, contentDescription = "close",
                                    tint = onBackGround
                                )
                            }
                        }
                        itemsIndexed(uiState.searchResult) { _, movie ->
                            SearchResultCard(
                                modifier = Modifier,
                                movie = movie
                            ) {
                                onNavigation.invoke(it.id.toString())
                            }
                        }
                        if(uiState.searchResult.isEmpty()){
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 77.sdp)
                                        .heightIn(max = screen.getHeight().dp),
                                    contentAlignment = Alignment.Center
                                ){
                                    Image(painter = painterResource(Res.drawable.search_empty), contentDescription = null)
                                }

                            }
                        }
                    }
                }
            }
            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(16.sdp, Alignment.Start)) {
                    itemsIndexed(uiState.topFiveMovie) { index, item ->
                        TopMovieCard(
                            data = item,
                            index = index,
                            onClick = {
                                onNavigation.invoke(it.id.toString())
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
                    edgePadding = 12.sdp,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex.value]),
                            color = searchContainer
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
                        .padding(top = 16.sdp)
                ) {
                    when (Tabs.entries[selectedTabIndex.value].text) {
                        "Upcoming" -> {
                            MovieFlowRow(Modifier, uiState.upcomingMovie) {
                                onNavigation.invoke(it.id.toString())
                            }
                        }

                        "Top Rated" -> {
                            MovieFlowRow(Modifier, uiState.topRatedMovie) {
                                onNavigation.invoke(it.id.toString())
                            }
                        }

                        "Popular" -> {
                            MovieFlowRow(Modifier, uiState.popularMovie) {
                                onNavigation.invoke(it.id.toString())
                            }
                        }

                        else -> {
                            MovieFlowRow(Modifier, uiState.nowPlayingMovie) {
                                onNavigation.invoke(it.id.toString())
                            }
                        }
                    }
                }
            }
        }
    }
}

//@OptIn(ExperimentalLayoutApi::class)
//@Composable
//fun NowPlayingMovies(
//    modifier: Modifier,
//    movies: List<Movie> = emptyList(),
//    onclick: (Movie) -> Unit = {},
//) {
//    FlowRow(
//        modifier = modifier.fillMaxSize(),
//        horizontalArrangement = Arrangement.SpaceEvenly,
//        verticalArrangement = Arrangement.spacedBy(14.sdp),
//        maxItemsInEachRow = 3
//    ) {
//        movies.forEachIndexed { index, movie ->
//            MovieCard(data = movie, index = index) {
//                onclick.invoke(it)
//            }
//        }
//    }
//}
//
//@OptIn(ExperimentalLayoutApi::class)
//@Composable
//fun UpcomingMovies(
//    modifier: Modifier,
//    movies: List<Movie> = emptyList(),
//    onclick: (Movie) -> Unit = {},
//) {
//    FlowRow(
//        modifier = modifier.fillMaxSize(),
//        horizontalArrangement = Arrangement.SpaceEvenly,
//        verticalArrangement = Arrangement.spacedBy(14.sdp),
//        maxItemsInEachRow = 3
//    ) {
//        movies.forEachIndexed { index, movie ->
//            MovieCard(data = movie, index = index) {
//                onclick.invoke(it)
//            }
//        }
//    }
//}
//
//@OptIn(ExperimentalLayoutApi::class)
//@Composable
//fun TopRatedMovies(
//    modifier: Modifier,
//    movies: List<Movie> = emptyList(),
//    onclick: (Movie) -> Unit = {},
//) {
//    FlowRow(
//        modifier = modifier.fillMaxSize(),
//        horizontalArrangement = Arrangement.SpaceEvenly,
//        verticalArrangement = Arrangement.spacedBy(14.sdp),
//        maxItemsInEachRow = 3
//    ) {
//        movies.forEachIndexed { index, movie ->
//            MovieCard(data = movie, index = index) {
//                onclick.invoke(it)
//            }
//        }
//    }
//}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MovieFlowRow(
    modifier: Modifier,
    movies: List<Movie> = emptyList(),
    onclick: (Movie) -> Unit = {},
) {
    FlowRow(
        modifier = modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalArrangement = Arrangement.spacedBy(14.sdp),
        maxItemsInEachRow = 3
    ) {
        movies.forEachIndexed { index, movie ->
            MovieCard(data = movie, index = index) {
                onclick.invoke(it)
            }
        }
    }
}