package de.sipgate.dachlatten.compose.text

import android.content.res.Configuration
import android.content.res.Resources
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import de.sipgate.dachlatten.compose.resolveLocale
import de.sipgate.dachlatten.text.TranslatedText
import de.sipgate.dachlatten.text.UiText
import de.sipgate.dachlatten.text.UiText.*

@Composable
public fun UiText.resolve(fallbackLocale: Locale? = null): String = when (this) {
    is DynamicString -> value()
    is StringResource -> stringResource(id = resId, formatArgs = args.toTypedArray())
    is MultiLangString -> {
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

public operator fun <T> Map<String, T>.get(locale: Locale): T? =
    this[locale.language.lowercase()] ?: this[locale.language.uppercase()]
