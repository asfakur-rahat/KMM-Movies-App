package com.ar.moviesapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.ar.moviesapp.core.utils.toOriginalImage
import com.ar.moviesapp.domain.model.Movie
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import movies.composeapp.generated.resources.Res
import movies.composeapp.generated.resources.ic_1
import movies.composeapp.generated.resources.ic_2
import movies.composeapp.generated.resources.ic_3
import movies.composeapp.generated.resources.ic_4
import movies.composeapp.generated.resources.ic_5
import org.jetbrains.compose.resources.painterResource

@Composable
fun TopMovieCard(
    modifier: Modifier = Modifier,
    data: Movie,
    index: Int = 0,
    onClick: (Movie) -> Unit = {},
) {
    val painterList = listOf(
        painterResource(Res.drawable.ic_1),
        painterResource(Res.drawable.ic_2),
        painterResource(Res.drawable.ic_3),
        painterResource(Res.drawable.ic_4),
        painterResource(Res.drawable.ic_5)
    )
    Box(modifier = modifier.height(230.dp).width(160.dp)) {
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
                CircularProgressIndicator()
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
                CircularProgressIndicator()
            },
            failure = {
                Text(text = "Can't fetch image now")
            }
        )
    }
}
