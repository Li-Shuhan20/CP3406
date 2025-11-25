package com.example.androidstarter.data

import com.example.androidstarter.data.local.BookDao
import com.example.androidstarter.data.local.BookEntity
import com.example.androidstarter.data.remote.OpenLibraryApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

data class ReadingStats(
    val total: Int,
    val finished: Int,
    val inProgress: Int
)

class BookRepository(
    private val bookDao: BookDao
) {
    fun getShelfBooks(): Flow<List<BookEntity>> = bookDao.getShelfBooks()

    fun getBookById(id: Long): Flow<BookEntity?> =
        bookDao.getBookById(id)

    fun searchBooks(keyword: String): Flow<List<BookEntity>> = flow {
        val trimmed = keyword.trim()

        if (trimmed.isBlank()) {
            emitAll(bookDao.getShelfBooks())
            return@flow
        }

        val remoteBooks: List<BookEntity> = try {
            val response = OpenLibraryApi.service.searchBooks(trimmed)

            response.docs
                .take(20)
                .map { doc ->
                    val title = doc.title ?: "Unknown title"
                    val author = doc.authorNames?.firstOrNull() ?: "Unknown author"

                    BookEntity(
                        id = 0,
                        title = title,
                        author = author,
                        rating = 0f,
                        progress = 0f,
                        isInShelf = false
                    )
                }
        } catch (e: Exception) {
            emptyList()
        }

        emit(remoteBooks)
    }


    suspend fun addBook(
        title: String,
        author: String,
        rating: Float = 0f
    ) {
        val entity = BookEntity(
            title = title,
            author = author,
            rating = rating,
            progress = 0f,
            isInShelf = true
        )
        bookDao.insertBook(entity)
    }

    suspend fun updateProgress(id: Long, newProgress: Float) {
        bookDao.updateProgress(id, newProgress)
    }

    suspend fun ensureSampleData() {
        if (bookDao.getCount() == 0) {
            val samples = listOf(
                BookEntity(
                    title = "The Great Gatsby",
                    author = "F. Scott Fitzgerald",
                    rating = 4.2f,
                    progress = 0.75f
                ),
                BookEntity(
                    title = "To Kill a Mockingbird",
                    author = "Harper Lee",
                    rating = 4.8f,
                    progress = 1.0f
                ),
                BookEntity(
                    title = "1984",
                    author = "George Orwell",
                    rating = 4.6f,
                    progress = 0.9f
                ),
                BookEntity(
                    title = "Dune",
                    author = "Frank Herbert",
                    rating = 4.3f,
                    progress = 0.25f
                )
            )
            samples.forEach { bookDao.insertBook(it) }
        }
    }

    suspend fun getReadingStats(): ReadingStats {
        val total = bookDao.getCount()
        val finished = bookDao.getFinishedCount()
        val inProgress = bookDao.getInProgressCount()
        return ReadingStats(
            total = total,
            finished = finished,
            inProgress = inProgress
        )
    }
}
