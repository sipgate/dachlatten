package de.sipgate.waffeleisen.flow

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.combine

class RefreshableFlow<T>(
    private val flow: SharedFlow<T>,
    private val refreshSignal: MutableStateFlow<Boolean>
) : Flow<T> {

    fun refresh() {
        refreshSignal.refresh()
    }

    override suspend fun collect(collector: FlowCollector<T>) {
        combine(flow, refreshSignal) { value, _ -> value }
            .collect { collector.emit(it) }
    }
}

fun <T> SharedFlow<T>.refreshableFlow(
    refreshSignal: RefreshSignal = MutableStateFlow(false)
) = RefreshableFlow(this, refreshSignal)

typealias RefreshSignal = MutableStateFlow<Boolean>

fun RefreshSignal.refresh() {
    value = !value
}
