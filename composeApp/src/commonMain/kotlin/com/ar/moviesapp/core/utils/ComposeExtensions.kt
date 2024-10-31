package com.ar.moviesapp.core.utils

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

fun PaddingValues.getSplashPadding(): PaddingValues {
    return PaddingValues(
        start = this.calculateStartPadding(LayoutDirection.Ltr),
        end = this.calculateEndPadding(LayoutDirection.Ltr),
    )
}

fun PaddingValues.getPaddingWithoutTop(): PaddingValues {
    return PaddingValues(
        start = this.calculateStartPadding(LayoutDirection.Ltr),
        end = this.calculateEndPadding(LayoutDirection.Ltr),
        bottom = this.calculateBottomPadding()
    )
}

fun PaddingValues.getExtraTopPadding(): PaddingValues {
    return PaddingValues(
        start = this.calculateStartPadding(LayoutDirection.Ltr) + 15.dp,
        end = this.calculateEndPadding(LayoutDirection.Ltr) + 15.dp,
        top = this.calculateTopPadding()
    )
}

fun PaddingValues.screenPadding(): PaddingValues {
    return PaddingValues(
        start = this.calculateStartPadding(LayoutDirection.Ltr) + 15.dp,
        end = this.calculateEndPadding(LayoutDirection.Ltr) + 15.dp,
        top = this.calculateTopPadding(),
        bottom = 15.dp
    )
}