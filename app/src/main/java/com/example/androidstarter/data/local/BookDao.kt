package com.example.androidstarter.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Query(
        """
        SELECT * FROM books 
        WHERE isInShelf = 1 
        ORDER BY id DESC
        """
    )
    fun getShelfBooks(): Flow<List<BookEntity>>

    @Query(
        """
        SELECT * FROM books 
        WHERE isInShelf = 1
          AND (
                title  LIKE '%' || :keyword || '%'
             OR author LIKE '%' || :keyword || '%'
          )
        ORDER BY title
        """
    )
    fun searchBooks(keyword: String): Flow<List<BookEntity>>

    @Query("SELECT * FROM books WHERE id = :id LIMIT 1")
    suspend fun getBookById(id: Long): BookEntity?

    @Query("UPDATE books SET progress = :newProgress WHERE id = :id")
    suspend fun updateProgress(id: Long, newProgress: Float)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: BookEntity): Long

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
