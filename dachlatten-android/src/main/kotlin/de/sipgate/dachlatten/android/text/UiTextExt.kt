package de.sipgate.dachlatten.android.text

import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import de.sipgate.dachlatten.text.TranslatedText
import de.sipgate.dachlatten.text.UiText
import java.util.Locale

fun UiText.asString(resources: Resources) = when (this) {
    is UiText.DynamicString -> value
    is UiText.StringResource -> resources.getString(resId, *(args.toTypedArray()))
    is UiText.MultiLangString -> {
        val arguments = args.toTypedArray()
        resources.configuration.getStringForLocales(language)?.format(*arguments)
            ?: fallbackResource?.let { resources.getString(it, *arguments) }
            ?: throw Resources.NotFoundException("Could not find multilang string")
    }
}

private fun Configuration.getStringForLocales(translations: TranslatedText): String? {
    val locale = resolveLocale(translations.keys)
    return translations[locale?.language?.lowercase()] ?: translations[locale?.language?.uppercase()]
}

private fun Configuration.resolveLocale(supportedLanguages: Set<String>): Locale? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        locales.getFirstMatch(supportedLanguages.toTypedArray())
    } else {
        @Suppress("DEPRECATION")
        locale
    }
}
