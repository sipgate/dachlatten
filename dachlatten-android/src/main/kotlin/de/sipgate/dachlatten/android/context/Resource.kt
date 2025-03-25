package de.sipgate.dachlatten.android.context

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.annotation.AnyRes
import androidx.core.net.toUri

/**
 * Get Uri referring to Drawable or any other given Resource type
 *
 * Adapted from:https://stackoverflow.com/a/36062748
 *
 * @param context - Context to resolve the Resource against
 * @param resourceId - Resource Id to be referenced
 * @return - Uri referencing the given Resource
 */
fun Context.getUriToDrawable(
    @AnyRes resourceId: Int,
): Uri = (
    ContentResolver.SCHEME_ANDROID_RESOURCE +
        "://" + resources.getResourcePackageName(resourceId) +
        '/' + resources.getResourceTypeName(resourceId) +
        '/' + resources.getResourceEntryName(resourceId)
    ).toUri()
