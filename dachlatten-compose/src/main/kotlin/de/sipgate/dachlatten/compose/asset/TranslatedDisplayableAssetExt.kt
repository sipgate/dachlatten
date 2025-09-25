package de.sipgate.dachlatten.compose.asset

import android.content.res.Configuration
import android.content.res.Resources
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.intl.Locale
import de.sipgate.dachlatten.asset.AssetUrl
import de.sipgate.dachlatten.asset.DisplayableAsset
import de.sipgate.dachlatten.asset.TranslatedDisplayableAsset
import de.sipgate.dachlatten.compose.resolveLocale
import de.sipgate.dachlatten.compose.text.get

@Composable
public fun TranslatedDisplayableAsset.resolve(
    fallbackLocale: Locale? = null,
    useDarkMode: Boolean = isSystemInDarkTheme()
): AssetUrl {
    val configuration = LocalConfiguration.current
    val displayableAsset = configuration.getAssetsForLocales(this, fallbackLocale)
        ?: throw Resources.NotFoundException("Could not find multilang string")

    return displayableAsset.resolve(useDarkMode)
}

private fun Configuration.getAssetsForLocales(
    translations: TranslatedDisplayableAsset,
    fallbackLocale: Locale?
): DisplayableAsset? {
    val resolvedLocale = resolveLocale(translations.keys)
    return resolvedLocale?.let { locale -> translations[locale] }
        ?: fallbackLocale?.let { fallback -> translations[fallback] }
}
