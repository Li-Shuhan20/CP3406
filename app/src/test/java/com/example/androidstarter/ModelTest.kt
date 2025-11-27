package com.example.androidstarter

import com.example.androidstarter.data.local.BookEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class ModelsTest {

    @Test
    fun bookEntity_toUiModel_copiesAllFields() {
        val entity = BookEntity(
            id = 1L,
            title = "Test Book",
            author = "Test Author",
            rating = 4.5f,
            progress = 0.3f,
            isInShelf = true,
            review = "Nice book"
        )

        val ui = entity.toUiModel()

        assertEquals(entity.id, ui.id)
        assertEquals(entity.title, ui.title)
        assertEquals(entity.author, ui.author)
        assertEquals(entity.rating, ui.rating, 0.0001f)
        assertEquals(entity.progress, ui.progress, 0.0001f)
    }
}