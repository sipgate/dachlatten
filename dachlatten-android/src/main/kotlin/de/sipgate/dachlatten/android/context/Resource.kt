package de.sipgate.dachlatten.android.context

import android.content.ContentResolver
import android.content.Context
import android.content.res.Resources
import android.net.Uri
import androidx.annotation.AnyRes

/**
 * Get Uri referring to Drawable or any other given Resource type
 *
 * Adapted from:https://stackoverflow.com/a/36062748
 *
 * @receiver - Resource to resolve the Resource Id against
 * @param resourceId - Resource Id to be referenced
 * @return - Uri referencing the given Resource
 */
public fun Resources.getUriToDrawable(
    @AnyRes resourceId: Int,
): Uri = Uri.Builder()
    .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
    .authority(getResourcePackageName(resourceId))
    .path(getResourceTypeName(resourceId))
    .appendPath(getResourceEntryName(resourceId))
    .build()

public fun Context.getUriToDrawable(
    @AnyRes resourceId: Int,
): Uri = resources.getUriToDrawable(resourceId)
