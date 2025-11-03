package de.sipgate.dachlatten.flow

import kotlinx.coroutines.flow.MutableStateFlow

public fun <K, V> MutableStateFlow<Map<K, V>>.mutateMap(
    transform: (MutableMap<K, V>) -> Unit
) {
    value = value.toMutableMap().let { transform(it); it.toMap() }
}

public fun <T> MutableStateFlow<List<T>>.mutateList(
    transform: (MutableList<T>) -> Unit
) {
    value = value.toMutableList().let { transform(it); it.toList() }
}
