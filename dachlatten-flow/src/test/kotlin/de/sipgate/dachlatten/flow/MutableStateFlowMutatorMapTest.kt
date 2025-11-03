package de.sipgate.dachlatten.flow

import de.sipgate.dachlatten.primitives.take
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertSame

class MutableStateFlowMutatorMapTest {

    private val initialValue = mapOf(1 to "one", 2 to "two")
    private val mutableStateFlow = MutableStateFlow(initialValue)

    @Test
    fun returnsMapAsIsIfTransformerDoesNothing() {
        mutableStateFlow.mutateMap {
            // do nothing
        }
        assertSame(initialValue, mutableStateFlow.value)
    }

    @Test
    fun setsMutatedMapWhenEntryIsAdded() {
        mutableStateFlow.mutateMap { map ->
            map[3] = "three"
        }

        assertNotEquals(initialValue, mutableStateFlow.value)
        assertEquals(3, mutableStateFlow.value.size)
        assertEquals(initialValue, mutableStateFlow.value.take(2))
        assertEquals("three", mutableStateFlow.value[3])
    }

    @Test
    fun setsMutatedMapWhenEntryIsRemoved() {
        mutableStateFlow.mutateMap { map ->
            map.remove(2)
        }

        assertNotEquals(initialValue, mutableStateFlow.value)
        assertEquals(1, mutableStateFlow.value.size)
        assertEquals("one", mutableStateFlow.value.entries.last().value)
    }
}
