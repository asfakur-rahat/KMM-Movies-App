package com.ar.moviesapp.core.utils

import platform.UIKit.UIScreen

actual class ScreenSize {
    actual fun getWidth(): Int {
        val widthPoints = UIScreen.mainScreen.bounds.size.width
        return widthPoints.toInt()  // Points are equivalent to dp on iOS
    }

    actual fun getHeight(): Int {
        val heightPoints = UIScreen.mainScreen.bounds.size.height
        return heightPoints.toInt()  // Points are equivalent to dp on iOS
    }
}