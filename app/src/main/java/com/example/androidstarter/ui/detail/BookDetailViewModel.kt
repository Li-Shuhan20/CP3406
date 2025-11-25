package com.example.androidstarter.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidstarter.data.BookRepository
import com.example.androidstarter.data.local.BookEntity
import com.example.androidstarter.toUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class BookDetailUiState(
    val isLoading: Boolean = true,
    val book: BookEntity? = null
)

class BookDetailViewModel(
    private val repository: BookRepository,
    private val bookId: Long
) : ViewModel() {

    private val _uiState = MutableStateFlow(BookDetailUiState())
    val uiState: StateFlow<BookDetailUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getBookById(bookId).collectLatest { entity ->
                _uiState.value = BookDetailUiState(
                    isLoading = false,
                    book = entity
                )
            }
        }
    }

    fun increaseProgress() {
        val current = _uiState.value.book ?: return
        val newProgress = (current.progress + 0.1f).coerceAtMost(1f)

        viewModelScope.launch {
            repository.updateProgress(current.id, newProgress)
        }
    }
}