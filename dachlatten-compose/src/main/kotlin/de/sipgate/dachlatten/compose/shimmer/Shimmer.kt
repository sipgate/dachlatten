package de.sipgate.dachlatten.compose.shimmer

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

@Composable
public fun Modifier.shimmer(
    visible: Boolean,
    defaultColor: Color,
    highlightColor: Color
): Modifier =
    when (visible) {
        true ->
            then(
                Modifier.animatedGradientBackground(
                    defaultColor = defaultColor,
                    highlightColor = highlightColor,
                )
            )

        else -> this
    }


private fun Modifier.animatedGradientBackground(
    defaultColor: Color,
    highlightColor: Color,
    startOffset: Duration = Duration.ZERO,
    duration: Duration = 2500.milliseconds
): Modifier = composed {
    val colorList = listOf(defaultColor, defaultColor, highlightColor, defaultColor, defaultColor)
    val infiniteTransition = rememberInfiniteTransition(label = "Shimmer Animation")
    val animatedOffset by
    infiniteTransition.animateFloat(
        initialValue = -2f,
        targetValue = 1f,
        animationSpec =
        infiniteRepeatable(
            animation = tween(duration.inWholeMilliseconds.toInt(), easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
            initialStartOffset =
            StartOffset(offsetMillis = startOffset.inWholeMilliseconds.toInt())
        ),
        label = "Shimmer Animation Offset"
    )

    this.then(
        Modifier.drawBehind {
            drawRect(
                brush =
                Brush.horizontalGradient(
                    colors = colorList,
                    startX = size.width * animatedOffset,
                    endX = size.width * (animatedOffset + 1f),
                    tileMode = TileMode.Clamp
                ),
                size = size
            )
        }
    )
}
