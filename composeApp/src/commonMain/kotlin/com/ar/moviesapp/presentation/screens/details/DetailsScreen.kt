package com.ar.moviesapp.presentation.screens.details

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ar.moviesapp.core.base.BaseUiState
import com.ar.moviesapp.core.components.Colors.backGround
import com.ar.moviesapp.core.components.Colors.onBackGround
import com.ar.moviesapp.core.components.Colors.onSearchContainer
import com.ar.moviesapp.core.components.Colors.rating
import com.ar.moviesapp.core.components.Colors.searchContainer
import com.ar.moviesapp.core.components.Colors.stroke
import com.ar.moviesapp.core.components.EmptyScreen
import com.ar.moviesapp.core.components.ErrorScreen
import com.ar.moviesapp.core.components.LoadingScreen
import com.ar.moviesapp.core.utils.cast
import com.ar.moviesapp.core.utils.getPaddingWithoutTop
import com.ar.moviesapp.core.utils.onlyYear
import com.ar.moviesapp.core.utils.toGenre
import com.ar.moviesapp.core.utils.toOriginalImage
import com.ar.moviesapp.core.utils.toRating
import com.ar.moviesapp.core.utils.toW500Image
import com.ar.moviesapp.data.remote.model.response.MovieCast
import com.ar.moviesapp.data.remote.model.response.MovieReview
import com.skydoves.landscapist.coil3.CoilImage
import kotlinx.coroutines.launch
import movies.composeapp.generated.resources.Res
import movies.composeapp.generated.resources.ic_back
import movies.composeapp.generated.resources.ic_bookmark
import movies.composeapp.generated.resources.ic_calender
import movies.composeapp.generated.resources.ic_genre
import movies.composeapp.generated.resources.ic_profile
import movies.composeapp.generated.resources.ic_rating
import movies.composeapp.generated.resources.ic_runtime
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DetailsScreen(
    paddingValues: PaddingValues,
    goBack: () -> Unit = {},
    movieId: Int = 0,
) {
    val viewModel = koinViewModel<DetailsViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.onTriggerEvent(DetailsScreenEvent.FetchMovieDetails(movieId))
    }

    when (uiState) {
        is BaseUiState.Data -> {
            val data = uiState.cast<BaseUiState.Data<DetailsScreenUiState>>().data
            DetailsScreenContent(
                paddingValues = paddingValues,
                uiState = data,
                onEvent = {
                    viewModel.onTriggerEvent(it)
                },
                goBack = {
                    goBack.invoke()
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailsScreenContent(
    paddingValues: PaddingValues,
    uiState: DetailsScreenUiState,
    onEvent: (DetailsScreenEvent) -> Unit,
    goBack: () -> Unit = {},
) {

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { 3 })
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }

    Column(
        modifier = Modifier.fillMaxSize().padding(paddingValues.getPaddingWithoutTop())
            .background(backGround),
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().height(100.dp)
                .padding(top = paddingValues.calculateTopPadding() + 10.dp)
                .padding(horizontal = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.clickable {
                    goBack.invoke()
                },
                imageVector = vectorResource(Res.drawable.ic_back),
                contentDescription = "Back",
                tint = onBackGround
            )
            Text(
                modifier = Modifier.weight(1f),
                text = "Details",
                color = onBackGround,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            )
            Icon(
                modifier = Modifier.clickable {
                    onEvent.invoke(DetailsScreenEvent.AddToWatchList(uiState.movieId))
                },
                imageVector = vectorResource(Res.drawable.ic_bookmark),
                contentDescription = "BookMark",
                tint = if (uiState.isBookMarked) rating else onBackGround
            )
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(bottom = 30.dp)
        ) {
            item {
                Box(
                    modifier = Modifier.fillMaxWidth().height(350.dp)
                ) {
                    CoilImage(
                        modifier = Modifier.fillMaxWidth().height(250.dp).align(Alignment.TopCenter)
                            .clip(RoundedCornerShape(bottomEnd = 25.dp, bottomStart = 25.dp)),
                        imageModel = {
                            uiState.movieDetails.backdropPath?.toOriginalImage()
                        },
                        failure = {
                            Box(modifier = Modifier.fillMaxSize().background(onBackGround))
                        }
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(start = 36.dp, end = 15.dp)
                            .align(Alignment.BottomCenter),
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.Start)
                    ) {
                        CoilImage(
                            imageModel = {
                                uiState.movieDetails.posterPath.toW500Image()
                            },
                            modifier = Modifier.height(180.dp).width(130.dp)
                                .clip(RoundedCornerShape(16.dp))
                        )
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = uiState.movieDetails.title,
                            color = onBackGround,
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontSize = 26.sp,
                                textAlign = TextAlign.Start
                            ),
                            lineHeight = 32.sp
                        )
                    }
                    Row(
                        modifier = Modifier.align(Alignment.BottomEnd)
                            .padding(end = 24.dp, bottom = 115.dp)
                            .background(backGround, shape = RoundedCornerShape(12.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Icon(
                            imageVector = vectorResource(Res.drawable.ic_rating),
                            contentDescription = null,
                            tint = rating
                        )
                        Text(
                            text = uiState.movieDetails.voteAverage.toRating(),
                            color = rating,
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontSize = 18.sp,
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                }
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth().height(48.dp).padding(horizontal = 50.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(
                        12.dp,
                        Alignment.CenterHorizontally
                    )
                ) {
                    InfoChip(
                        icon = Res.drawable.ic_calender,
                        text = uiState.movieDetails.releaseDate.onlyYear()
                    )
                    InfoChip(
                        icon = Res.drawable.ic_runtime,
                        text = "${uiState.movieDetails.runtime} Minutes"
                    )
                    InfoChip(
                        icon = Res.drawable.ic_genre,
                        text = uiState.movieDetails.genres.toGenre()
                    )
                }
            }
            item {
                TabRow(
                    containerColor = Color.Transparent,
                    contentColor = onBackGround,
                    selectedTabIndex = selectedTabIndex.value,
                    divider = {},
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex.value]),
                            color = searchContainer
                        )
                    },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 36.dp)
                ) {
                    MovieTabs.entries.forEachIndexed { index, currentTab ->
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
                    when (MovieTabs.entries[selectedTabIndex.value].text) {
                        "Cast" -> MovieCastList(Modifier, uiState.movieCast.take(10))
                        "Reviews" -> MovieReviewList(Modifier, uiState.movieReview)
                        else -> {
                            Box(
                                modifier = Modifier.fillMaxWidth().height(300.dp),
                                contentAlignment = Alignment.TopStart
                            ) {
                                Text(
                                    modifier = Modifier.padding(15.dp),
                                    text = uiState.movieDetails.overview,
                                    color = onBackGround,
                                    style = MaterialTheme.typography.labelLarge.copy(
                                        fontSize = 20.sp,
                                        textAlign = TextAlign.Start
                                    ),
                                    lineHeight = 22.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MovieReviewList(
    modifier: Modifier,
    reviews: List<MovieReview> = emptyList(),
) {
    FlowRow(
        modifier = modifier.fillMaxSize().padding(horizontal = 15.dp).padding(bottom = 30.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
        maxItemsInEachRow = 1
    ) {
        if (reviews.isEmpty()) {
            Box(
                modifier = Modifier.height(300.dp).fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No Reviews",
                    color = onBackGround,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center
                    )
                )
            }
        } else {
            reviews.forEachIndexed { _, review ->
                MovieReviewCard(Modifier, review)
            }
        }
    }
}

@Composable
fun MovieReviewCard(
    modifier: Modifier,
    review: MovieReview,
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier.fillMaxWidth()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CoilImage(
                modifier = Modifier.size(80.dp).border(1.dp, stroke, CircleShape).clip(CircleShape),
                imageModel = {
                    review.authorDetails.avatarPath?.toOriginalImage()
                },
                failure = {
                    Icon(
                        imageVector = vectorResource(Res.drawable.ic_profile),
                        contentDescription = null,
                        tint = onBackGround
                    )
                }
            )
            Spacer(Modifier.height(8.dp))
            Text(text = review.authorDetails.rating?.toString() ?: "0", color = stroke)
        }
        Spacer(Modifier.width(16.dp))
        Column(horizontalAlignment = Alignment.Start) {
            Text(
                text = review.author,
                color = onBackGround,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontSize = 20.sp,
                    textAlign = TextAlign.Start
                )
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = review.content,
                color = onBackGround,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis,
                fontSize = 16.sp
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MovieCastList(
    modifier: Modifier,
    casts: List<MovieCast> = emptyList(),
) {
    FlowRow(
        modifier = modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
        maxItemsInEachRow = 2
    ) {
        casts.forEachIndexed { _, cast ->
            CastCard(cast)
        }
    }
}

@Composable
fun CastCard(
    cast: MovieCast,
) {
    Box(modifier = Modifier.size(width = 140.dp, height = 170.dp)) {
        CoilImage(
            modifier = Modifier.size(120.dp).align(Alignment.TopCenter).clip(CircleShape),
            imageModel = {
                cast.profilePath?.toOriginalImage()
            },
            failure = {
                Icon(
                    imageVector = vectorResource(Res.drawable.ic_profile),
                    contentDescription = null,
                    tint = onBackGround
                )
            }
        )
        Text(
            modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth(),
            text = cast.name,
            color = onBackGround,
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}


enum class MovieTabs(val text: String) {
    AboutMovie("About Movie"),
    Review("Reviews"),
    Cast("Cast")
}

@Composable
fun InfoChip(
    icon: DrawableResource,
    text: String,
    color: Color = onSearchContainer
) {
    Row {
        Icon(
            imageVector = vectorResource(icon),
            contentDescription = null,
            tint = color
        )
        Spacer(Modifier.width(3.dp))
        Text(
            text = text,
            color = color,
            style = MaterialTheme.typography.labelLarge
        )
    }
}