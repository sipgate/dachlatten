package de.sipgate.dachlatten.primitives

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class BooleanNullableExtTest {
    @Test
    fun arrayOfAllTruesReturnsTrue() {
        val allTrues = arrayOf<Boolean?>(true, true, true)
        assertTrue(allTrues.allTrue)
    }

    @Test
    fun arrayOfNotAllTruesReturnsFalse() {
        val someTrues = arrayOf<Boolean?>(true, false, true)
        assertFalse(someTrues.allTrue)
    }

    @Test
    fun arrayOfWithSomeNullsReturnsFalse() {
        val someTrues = arrayOf(true, null, true)
        assertFalse(someTrues.allTrue)
    }

    @Test
    fun arrayOfNonTruesReturnsFalse() {
        val noTrues = arrayOf<Boolean?>(false, false, false)
        assertFalse(noTrues.allTrue)
    }


    /**
     * This is needed to be compatible with the all operators
     *  "Vacuous truth"  behaviour.
     */
    @Test
    fun emptyArrayReturnsTrue() {
        val emptyList = arrayOf<Boolean?>()
        assertTrue(emptyList.allTrue)
    }
}