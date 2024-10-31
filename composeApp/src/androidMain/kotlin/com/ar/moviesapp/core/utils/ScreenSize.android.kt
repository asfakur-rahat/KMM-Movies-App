package com.ar.moviesapp.core.utils

import android.content.Context
import android.util.DisplayMetrics

actual class ScreenSize(private val context: Context) {
    private fun pxToDp(px: Int): Int {
        val density = context.resources.displayMetrics.density
        return (px / density).toInt()
    }
    actual fun getWidth(): Int {
        val metrics = DisplayMetrics()
        context.resources.displayMetrics?.let { metrics.setTo(it) }
        return pxToDp(metrics.widthPixels)
    }
    actual fun getHeight(): Int {
        val metrics = DisplayMetrics()
        context.resources.displayMetrics?.let { metrics.setTo(it) }
        return pxToDp(metrics.heightPixels)
    }
}