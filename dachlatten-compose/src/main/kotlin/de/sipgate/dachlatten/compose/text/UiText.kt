package de.sipgate.dachlatten.compose.text

import android.content.res.Resources
import android.os.LocaleList
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource

sealed interface UiText {
    data class DynamicString(val value: String) : UiText

    class StringResource(
        @StringRes val resId: Int,
        vararg val args: Any,
    ) : UiText

    class MultiLangString(
        private val language: Map<String, String>,
        @StringRes val fallbackResource: Int? = null,
        vararg val args: Any,
    ) : UiText {
        fun LocaleList.getStringForLocales(): String? {
            val locale = getFirstMatch(language.keys.toTypedArray())
            return language[locale?.language]
        }
    }

    fun asString(resources: Resources) = when (this) {
        is DynamicString -> value
        is StringResource -> resources.getString(resId, *args)
        is MultiLangString -> {
            resources.configuration.locales.getStringForLocales()
                ?: fallbackResource?.let { resources.getString(it, *args) }
                ?: throw Resources.NotFoundException("Could not find multilang string")
        }
    }

    @Composable
    fun asString() = when (this) {
            is DynamicString -> value
            is StringResource -> stringResource(id = resId, formatArgs = args)
            is MultiLangString -> {
                LocalConfiguration.current.locales.getStringForLocales()
                    ?: fallbackResource?.let { stringResource(id = it, formatArgs = args) }
                    ?: throw Resources.NotFoundException("Could not find multilang string")
            }
        }
}
