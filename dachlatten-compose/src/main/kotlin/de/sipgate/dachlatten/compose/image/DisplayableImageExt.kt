package de.sipgate.dachlatten.compose.image

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import de.sipgate.dachlatten.image.DisplayableImage
import de.sipgate.dachlatten.image.ImageUrl

@Composable
fun DisplayableImage.getThemeDependingUri(): ImageUrl =
    when {
        isSystemInDarkTheme() -> dark
        else -> light
    }
