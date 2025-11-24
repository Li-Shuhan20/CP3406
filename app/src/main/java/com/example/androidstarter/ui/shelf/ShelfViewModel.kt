package com.example.androidstarter.ui.shelf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.androidstarter.data.BookRepository
import com.example.androidstarter.data.local.BookEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class ShelfUiState(
    val isLoading: Boolean = true,
    val books: List<BookEntity> = emptyList()
)

class ShelfViewModel(
    private val repository: BookRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ShelfUiState())
    val uiState: StateFlow<ShelfUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.ensureSampleData()
            repository.getShelfBooks().collectLatest { list ->
                _uiState.value = ShelfUiState(
                    isLoading = false,
                    books = list
                )
            }
        }
    }

    fun addDemoBook() {
        viewModelScope.launch {
            repository.addBook(
                title = "New Book ${(0..999).random()}",
                author = "Unknown",
                rating = 4.0f
            )
        }
    }

    fun increaseProgress(book: BookEntity) {
        val newProgress = (book.progress + 0.1f).coerceAtMost(1f)
        viewModelScope.launch {
            repository.updateProgress(book.id, newProgress)
        }
    }
}

class ShelfViewModelFactory(
    private val repository: BookRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShelfViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ShelfViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
    }
}
