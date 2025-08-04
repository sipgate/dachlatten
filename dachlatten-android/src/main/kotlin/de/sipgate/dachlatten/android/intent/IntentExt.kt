package de.sipgate.dachlatten.android.intent

import android.content.Intent
import android.os.Build
import android.os.Parcelable
import java.io.Serializable
import kotlin.reflect.KClass

fun <T : Serializable> Intent.getSerializableExtraCompat(name: String, clazz: KClass<T>): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getSerializableExtra<T>(name, clazz.java)
    } else {
        @Suppress("DEPRECATION", "UNCHECKED_CAST")
        getSerializableExtra(name) as? T
    }
}

fun <T : Parcelable> Intent.getParcelableExtraCompat(name: String, clazz: KClass<T>): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelableExtra(name, clazz.java)
    } else {
        @Suppress("DEPRECATION", "UNCHECKED_CAST")
        getParcelableExtra(name) as? T
    }
}
