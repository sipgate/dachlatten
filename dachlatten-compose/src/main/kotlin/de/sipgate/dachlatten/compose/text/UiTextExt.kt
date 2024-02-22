package de.sipgate.dachlatten.compose.text

import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import de.sipgate.dachlatten.text.TranslatedText
import de.sipgate.dachlatten.text.UiText
import java.util.Locale

@Composable
fun UiText.asString() = when (this) {
    is UiText.DynamicString -> value
    is UiText.StringResource -> stringResource(id = resId, formatArgs = args.toTypedArray())
    is UiText.MultiLangString -> {
        val arguments = args.toTypedArray()
        LocalConfiguration.current.getStringForLocales(language) ?.format(*arguments)
            ?: fallbackResource?.let { stringResource(id = it, formatArgs = arguments) }
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
