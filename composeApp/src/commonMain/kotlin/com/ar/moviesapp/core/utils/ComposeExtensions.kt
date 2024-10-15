package com.ar.moviesapp.core.utils

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

fun PaddingValues.getSplashPadding(): PaddingValues {
    return PaddingValues(
        top = 0.dp,
        bottom = 0.dp,
        start = this.calculateStartPadding(LayoutDirection.Ltr),
        end = this.calculateEndPadding(LayoutDirection.Ltr),
    )
}