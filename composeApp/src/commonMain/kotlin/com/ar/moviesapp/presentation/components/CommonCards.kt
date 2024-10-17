package com.ar.moviesapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ar.moviesapp.core.components.Colors
import com.ar.moviesapp.core.components.Colors.onBackGround
import com.ar.moviesapp.core.components.Colors.rating
import com.ar.moviesapp.core.utils.toOriginalImage
import com.ar.moviesapp.core.utils.toRating
import com.ar.moviesapp.data.remote.model.response.SearchedMovie
import com.ar.moviesapp.domain.model.Movie
import com.ar.moviesapp.domain.model.TrendingMovie
import com.ar.moviesapp.presentation.screens.details.InfoChip
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import io.ktor.sse.END_OF_LINE
import movies.composeapp.generated.resources.Res
import movies.composeapp.generated.resources.ic_1
import movies.composeapp.generated.resources.ic_2
import movies.composeapp.generated.resources.ic_3
import movies.composeapp.generated.resources.ic_4
import movies.composeapp.generated.resources.ic_5
import movies.composeapp.generated.resources.ic_calender
import movies.composeapp.generated.resources.ic_rating
import movies.composeapp.generated.resources.ic_runtime
import org.jetbrains.compose.resources.painterResource

@Composable
fun TopMovieCard(
    modifier: Modifier = Modifier,
    data: TrendingMovie,
    index: Int = 0,
    onClick: (TrendingMovie) -> Unit = {},
) {
    val painterList = listOf(
        painterResource(Res.drawable.ic_1),
        painterResource(Res.drawable.ic_2),
        painterResource(Res.drawable.ic_3),
        painterResource(Res.drawable.ic_4),
        painterResource(Res.drawable.ic_5)
    )
    Box(modifier = modifier.height(230.dp).width(160.dp).clickable { onClick.invoke(data) }) {
        CoilImage(
            modifier = Modifier
                .padding(start = 15.dp)
                .height(210.dp)
                .width(145.dp)
                .align(Alignment.TopEnd)
                .clip(RoundedCornerShape(16.dp)),
            imageModel = { data.posterPath.toOriginalImage() },
            imageOptions = ImageOptions(
                contentScale = ContentScale.FillBounds
            ),
            loading = {
                CircularProgressIndicator(Modifier.size(48.dp).align(Alignment.Center))
            },
            failure = {
                Text(text = "Can't fetch image now")
            }
        )
        Image(
            painter = painterList[index],
            contentDescription = null,
            modifier = Modifier
                .padding(bottom = 5.dp)
                .align(Alignment.BottomStart)
                .size(width = 70.dp, height = 90.dp),
            contentScale = ContentScale.FillHeight
        )
    }
}

@Composable
fun MovieCard(
    modifier: Modifier = Modifier,
    data: Movie,
    index: Int = 0,
    onClick: (Movie) -> Unit = {},
) {
    Box(modifier = modifier.height(145.dp).width(100.dp).clickable { onClick.invoke(data) }) {
        CoilImage(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
                .clip(RoundedCornerShape(16.dp)),
            imageModel = { data.posterPath.toOriginalImage() },
            imageOptions = ImageOptions(
                contentScale = ContentScale.FillBounds
            ),
            loading = {
                CircularProgressIndicator(Modifier.size(48.dp).align(Alignment.Center))
            },
            failure = {
                Text(text = "Can't fetch image now")
            }
        )
    }
}

@Composable
fun SearchResultCard(
    modifier: Modifier = Modifier,
    movie: SearchedMovie,
    onClick: (SearchedMovie) -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp)
            .padding(horizontal = 20.dp)
            .clickable {
                onClick.invoke(movie)
            },
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center
    ) {
        CoilImage(
            modifier = Modifier
                .fillMaxHeight()
                .width(145.dp)
                .clip(RoundedCornerShape(16.dp)),
            imageModel = {
                movie.posterPath?.toOriginalImage()
            },
            loading = {
                CircularProgressIndicator(Modifier.size(48.dp).align(Alignment.Center))
            },
            failure = {
                Text(text = "Can't fetch image now")
            }
        )
        Spacer(Modifier.width(16.dp))
        Column(
            modifier = Modifier.weight(1f).fillMaxHeight().padding(vertical = 12.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = movie.title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = onBackGround
                )
            )
            Column {
                InfoChip(
                    icon = Res.drawable.ic_rating,
                    text = movie.voteAverage.toRating(),
                    color = rating
                )
                Spacer(Modifier.height(8.dp))
                InfoChip(
                    icon = Res.drawable.ic_calender,
                    text = movie.releaseDate,
                    color = onBackGround
                )
            }
        }
    }
}
