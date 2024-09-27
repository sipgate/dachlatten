package de.sipgate.dachlatten.compose.asset

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import de.sipgate.dachlatten.asset.AssetUrl
import de.sipgate.dachlatten.asset.DisplayableAsset

@Composable
fun DisplayableAsset.resolve(
    useDarkMode: Boolean = isSystemInDarkTheme()
): AssetUrl = when {
    useDarkMode -> dark ?: light
    else -> light
}
