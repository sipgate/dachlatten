package de.sipgate.dachlatten.compose.text

import android.content.res.Configuration
import android.content.res.Resources
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import de.sipgate.dachlatten.compose.resolveLocale
import de.sipgate.dachlatten.text.TranslatedText
import de.sipgate.dachlatten.text.UiText
import java.util.Locale

@Composable
fun UiText.resolve(fallbackLocale: Locale? = null) = when (this) {
    is UiText.DynamicString -> value
    is UiText.StringResource -> stringResource(id = resId, formatArgs = args.toTypedArray())
    is UiText.MultiLangString -> {
        val arguments = args.toTypedArray()
        LocalConfiguration.current.getStringForLocales(language, fallbackLocale) ?.format(*arguments)
            ?: fallbackResource?.let { stringResource(id = it, formatArgs = arguments) }
            ?: throw Resources.NotFoundException("Could not find multilang string")
    }
}

private fun Configuration.getStringForLocales(translations: TranslatedText, fallbackLocale: Locale?): String? {
    val resolvedLocale = resolveLocale(translations.keys)
    return resolvedLocale?.let { locale -> translations[locale] }
        ?: fallbackLocale?.let { fallback -> translations[fallback] }
}

operator fun <T> Map<String, T>.get(locale: Locale): T? =
    this[locale.language.lowercase()] ?: this[locale.language.uppercase()]
