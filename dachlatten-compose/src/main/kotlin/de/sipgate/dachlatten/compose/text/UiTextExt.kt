package de.sipgate.dachlatten.compose.text

import android.content.res.Resources
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import de.sipgate.dachlatten.android.text.UiText

@Composable
fun UiText.asString() = when (this) {
    is UiText.DynamicString -> value
    is UiText.StringResource -> stringResource(id = resId, formatArgs = args.toTypedArray())
    is UiText.MultiLangString -> {
        val arguments = args.toTypedArray()
        LocalConfiguration.current.getStringForLocales() ?.format(*arguments)
            ?: fallbackResource?.let { stringResource(id = it, formatArgs = arguments) }
            ?: throw Resources.NotFoundException("Could not find multilang string")
    }
}
