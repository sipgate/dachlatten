package de.sipgate.dachlatten.compose.color

import androidx.compose.ui.graphics.Color

public fun blend(
    color1: Color,
    color2: Color,
    ratio: Float,
): Color {
    val inverseRatio = 1f - ratio

    val red = color1.red * ratio + color2.red * inverseRatio
    val green = color1.green * ratio + color2.green * inverseRatio
    val blue = color1.blue * ratio + color2.blue * inverseRatio
    val alpha = color1.alpha * ratio + color2.alpha * inverseRatio

    return Color(red, green, blue, alpha)
}
