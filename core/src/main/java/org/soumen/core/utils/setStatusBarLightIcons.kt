package org.soumen.core.utils

import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat

fun setStatusBarLightIcons(light: Boolean) {
    val activity = CurrentActivityHolder.activity ?: return
    val window = activity.window
    WindowCompat.setDecorFitsSystemWindows(window, false)
    WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = light
}