package com.example.androidstarter.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Query(" SELECT * FROM books WHERE isInShelf = 1")
    fun getShelfBooks(): Flow<List<BookEntity>>

    @Query(
        """
        SELECT * FROM books 
        WHERE title  LIKE '%' || :keyword || '%'
           OR author LIKE '%' || :keyword || '%'
        ORDER BY title
        """
    )
    fun searchBooks(keyword: String): Flow<List<BookEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: BookEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooks(books: List<BookEntity>)

    @Query("SELECT * FROM books WHERE id = :id LIMIT 1")
    fun getBookById(id: Long): Flow<BookEntity?>

    @Query("UPDATE books SET progress = :newProgress WHERE id = :id")
    suspend fun updateProgress(id: Long, newProgress: Float)

    @Update
    suspend fun updateBook(book: BookEntity)

    @Delete
    suspend fun deleteBook(book: BookEntity)

    @Query("SELECT COUNT(*) FROM books")
    suspend fun getCount(): Int

    @Query("SELECT COUNT(*) FROM books WHERE progress >= 0.99")
    suspend fun getFinishedCount(): Int

    @Query("SELECT COUNT(*) FROM books WHERE progress > 0 AND progress < 0.99")
    suspend fun getInProgressCount(): Int
}
