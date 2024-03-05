package de.sipgate.dachlatten.compose.asset

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import de.sipgate.dachlatten.asset.DisplayableAsset
import de.sipgate.dachlatten.asset.AssetUrl

@Composable
fun DisplayableAsset.getThemeDependingUri(): AssetUrl =
    when {
        isSystemInDarkTheme() -> dark
        else -> light
    }
