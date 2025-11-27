package com.example.androidstarter.data

import org.junit.Assert.assertEquals
import org.junit.Test

class ReadingStatsTest {

    @Test
    fun readingStats_holdsCorrectValues() {
        val stats = ReadingStats(
            total = 10,
            finished = 4,
            inProgress = 3
        )

        assertEquals(10, stats.total)
        assertEquals(4, stats.finished)
        assertEquals(3, stats.inProgress)
    }
}