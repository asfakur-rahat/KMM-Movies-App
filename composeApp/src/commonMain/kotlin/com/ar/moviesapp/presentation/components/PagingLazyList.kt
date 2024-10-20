package com.ar.moviesapp.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.cash.paging.LoadStateError
import app.cash.paging.LoadStateLoading
import app.cash.paging.LoadStateNotLoading
import app.cash.paging.compose.LazyPagingItems
import com.ar.moviesapp.core.components.Colors
import com.ar.moviesapp.core.components.Colors.onBackGround
import network.chaintech.sdpcomposemultiplatform.sdp

//@Composable
//fun <T : Any> CommonPagingList(
//    modifier: Modifier = Modifier,
//    data: LazyPagingItems<T>,
//    hasOptionalList: Boolean = false,
//    optionalTopLList: List<T> = emptyList(),
//    optionalHeaderTitle: String = "",
//    paddingValues: PaddingValues = PaddingValues(16.dp),
//    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(8.dp),
//    optionalContent: @Composable (T) -> Unit = {},
//    bottomPadding: PaddingValues = PaddingValues(0.dp),
//    content: @Composable (T? ,Modifier) -> Unit,
//) {
//    LazyColumn(
//        modifier = modifier
//            .fillMaxSize()
//            .background(Colors.backGround),
//        verticalArrangement = verticalArrangement,
//        horizontalAlignment = Alignment.CenterHorizontally,
//        contentPadding = paddingValues
//    ) {
//
//        item {
//            AnimatedVisibility(hasOptionalList) {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .clip(
//                            RoundedCornerShape(
//                                topEnd = 10.sdp,
//                                topStart = 10.sdp
//                            )
//                        )
//                        .background(Colors.backGround)
//                ) {
//                    Text(
//                        modifier = Modifier.padding(16.dp),
//                        text = optionalHeaderTitle,
//                        style = MaterialTheme.typography.titleMedium,
//                        color = Colors.onBackGround
//                    )
//                }
//            }
//        }
//        items(optionalTopLList) { item ->
//            optionalContent(item)
//        }
//        items(data.itemCount) { index ->
//            val item = data[index]
//            // Check if this is the last index
//            if (index == data.itemCount - 1) {
//                // This is the last index, you can add padding or any other logic here
//                content(item, Modifier.padding(bottomPadding))
//            }else{
//                content(item, Modifier)
//            }
//        }
//        data.loadState.apply {
//            when {
//                refresh is LoadStateNotLoading && data.itemCount < 1 -> {
//                    item {
//                        Box(
//                            modifier = Modifier.fillParentMaxSize(),
//                            contentAlignment = Alignment.Center
//                        ) {
//                            Text(
//                                text = "No Items",
//                                modifier = Modifier.align(Alignment.Center),
//                                textAlign = TextAlign.Center
//                            )
//                        }
//                    }
//                }
//
//                refresh is LoadStateLoading -> {
//                    item {
//                        Box(
//                            modifier = Modifier.fillParentMaxSize(),
//                            contentAlignment = Alignment.Center
//                        ) {
//                            CircularProgressIndicator(
//                                modifier.align(Alignment.Center),
//                                color = onBackGround
//                            )
//                        }
//                    }
//                }
//
//                append is LoadStateLoading -> {
//                    item {
//                        CircularProgressIndicator(
//                            color = onBackGround,
//                            modifier = Modifier.fillMaxWidth()
//                                .padding(13.sdp)
//                                .wrapContentWidth(Alignment.CenterHorizontally)
//                        )
//                    }
//                }
//
//                refresh is LoadStateError -> {
//                    item {
//                        ErrorView(
//                            message = "No Internet Connection",
//                            onClickRetry = { data.retry() },
//                            modifier = Modifier.fillParentMaxSize()
//                        )
//                    }
//                }
//
//                append is LoadStateError -> {
//                    item {
//                        ErrorItem(
//                            message = "No Internet Connection",
//                            onClickRetry = { data.retry() },
//                        )
//                    }
//                }
//            }
//        }
//    }
//}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T : Any> CommonPagingList(
    modifier: Modifier = Modifier,
    data: LazyPagingItems<T>,
    hasOptionalList: Boolean = false,
    optionalTopLList: List<T> = emptyList(),
    optionalHeaderTitle: String = "",
    paddingValues: PaddingValues = PaddingValues(16.dp),
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(8.dp),
    optionalContent: @Composable (T) -> Unit = {},
    bottomPadding: PaddingValues = PaddingValues(0.dp),
    columns: Int = 3,  // Number of columns for the grid
    content: @Composable (T? , Modifier) -> Unit,
) {
    LazyVerticalGrid(
        modifier = modifier
            .fillMaxSize()
            .background(Colors.backGround),
        columns = GridCells.Fixed(columns),  // Define the number of columns here
        contentPadding = paddingValues,
        verticalArrangement = verticalArrangement
    ) {

        item(span = { GridItemSpan(columns) }) {
            AnimatedVisibility(hasOptionalList) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(
                                topEnd = 10.sdp,
                                topStart = 10.sdp
                            )
                        )
                        .background(Colors.backGround)
                ) {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = optionalHeaderTitle,
                        style = MaterialTheme.typography.titleMedium,
                        color = Colors.onBackGround
                    )
                }
            }
        }
        items(optionalTopLList) { item ->
            optionalContent(item)
        }
        items(data.itemCount) { index ->
            val item = data[index]
            // Check if this is the last index
            if (index == data.itemCount - 1) {
                // This is the last index, you can add padding or any other logic here
                content(item, Modifier.padding(bottomPadding))
            } else {
                content(item, Modifier)
            }
        }
        data.loadState.apply {
            when {
                refresh is LoadStateNotLoading && data.itemCount < 1 -> {
                    item(span = { GridItemSpan(columns) }) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No Items",
                                modifier = Modifier.align(Alignment.Center),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                refresh is LoadStateLoading -> {
                    item(span = { GridItemSpan(columns) }) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier.align(Alignment.Center),
                                color = Colors.onBackGround
                            )
                        }
                    }
                }

                append is LoadStateLoading -> {
                    item(span = { GridItemSpan(columns) }) {
                        CircularProgressIndicator(
                            color = Colors.onBackGround,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(13.sdp)
                                .wrapContentWidth(Alignment.CenterHorizontally)
                        )
                    }
                }

                refresh is LoadStateError -> {
                    item(span = { GridItemSpan(columns) }) {
                        ErrorView(
                            message = "No Internet Connection",
                            onClickRetry = { data.retry() },
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                append is LoadStateError -> {
                    item(span = { GridItemSpan(columns) }) {
                        ErrorItem(
                            message = "No Internet Connection",
                            onClickRetry = { data.retry() },
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun ErrorItem(
    message: String,
    modifier: Modifier = Modifier,
    onClickRetry: () -> Unit,
) {
    Row(
        modifier = modifier.padding(14.sdp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = message,
            maxLines = 1,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Red
        )
        OutlinedButton(onClick = onClickRetry) {
            Text(text = "Try again")
        }
    }
}

@Composable
private fun ErrorView(
    message: String,
    modifier: Modifier = Modifier,
    onClickRetry: () -> Unit,
) {
    Column(
        modifier = modifier.padding(14.sdp).onPlaced { cor ->

        },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            maxLines = 1,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Red
        )
        OutlinedButton(
            onClick = onClickRetry,
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.sdp)
                .wrapContentWidth(Alignment.CenterHorizontally)
        ){
            Text(text = "Try again")
        }
    }
}