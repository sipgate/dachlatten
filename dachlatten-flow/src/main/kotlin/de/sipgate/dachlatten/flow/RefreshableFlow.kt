package de.sipgate.dachlatten.flow

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow


/**
 * Connects a given SharedFlow with a RefreshSignal.
 *
 * This will re-emit the last value whenever the RefreshSignal is triggered.
 * Because the flow is going to stay connected, this only works with HotFlows (hence the SharedFlow)
 *
 * @param refreshSignal Can be any Channel<Unit> or a RefreshSignal created with `createRefreshSignal()`
 */
public class RefreshableFlow<T>(
    private val sharedFlow: SharedFlow<T>,
    private val refreshSignal: RefreshSignal
) : Flow<T> {

    /**
     * Exposes the RefreshSignal.refresh function for your convenience.
     */
    public fun refresh() {
        refreshSignal.refresh()
    }

    override suspend fun collect(collector: FlowCollector<T>) {
        combine(sharedFlow, refreshSignal.receiveAsFlow()) { value, _ -> value }
            .collect { collector.emit(it) }
    }
}

public fun <T> SharedFlow<T>.refreshableFlow(
    refreshSignal: RefreshSignal = createRefreshSignal()
): RefreshableFlow<T> = RefreshableFlow(this, refreshSignal)

public typealias RefreshSignal = Channel<Unit>

public fun createRefreshSignal(): RefreshSignal =
    Channel<Unit>(capacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST).also { it.refresh() }

public fun RefreshSignal.refresh() {
    trySendBlocking(Unit)
}
