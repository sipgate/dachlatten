package de.sipgate.dachlatten.datetime

import org.junit.jupiter.api.Test
import java.util.Date
import java.util.TimeZone
import kotlin.test.assertEquals

class DateExtTest {

    private val berlinZone = TimeZone.getTimeZone("Europe/Berlin")

    private val firstOfJanuary2023 = Date(1672574400000)
    private val firstOfJuly2023 = Date(1688212800000)

    @Test
    fun conversionFromDateToLocalDateReturnsCorrectValue() {
        val convertedDate = firstOfJanuary2023.toLocalDateTime(berlinZone)
        assertEquals(2023, convertedDate.year)
        assertEquals(1, convertedDate.monthValue)
        assertEquals(1, convertedDate.dayOfMonth)
        assertEquals(13, convertedDate.hour) // adjusted GMT -> Berlin Wintertime
        assertEquals(0, convertedDate.minute)
        assertEquals(0, convertedDate.second)
    }

    @Test
    fun conversionFromDateToLocalDateCorrectlyAccountsForDaylightSavings() {
        val convertedDate = firstOfJuly2023.toLocalDateTime(berlinZone)
        assertEquals(2023, convertedDate.year)
        assertEquals(7, convertedDate.monthValue)
        assertEquals(1, convertedDate.dayOfMonth)
        assertEquals(14, convertedDate.hour) // adjusted GMT -> Berlin Summertime
        assertEquals(0, convertedDate.minute)
        assertEquals(0, convertedDate.second)
    }
}
