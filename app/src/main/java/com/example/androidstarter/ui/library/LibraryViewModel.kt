package com.example.androidstarter.ui.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.androidstarter.data.BookRepository
import com.example.androidstarter.data.local.BookEntity
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LibraryUiState(
    val query: String = "",
    val results: List<BookEntity> = emptyList(),
    val isLoading: Boolean = false
)

class LibraryViewModel(
    private val repository: BookRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LibraryUiState())
    val uiState: StateFlow<LibraryUiState> = _uiState.asStateFlow()

    private var searchJob: Job? = null

    fun onQueryChange(newQuery: String) {
        _uiState.update { it.copy(query = newQuery) }
    }

    fun performSearch() {
        val keyword = _uiState.value.query.trim()

        searchJob?.cancel()

        if (keyword.isBlank()) {
            _uiState.update { it.copy(results = emptyList(), isLoading = false) }
            return
        }

        searchJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            repository.searchBooks(keyword).collect { list ->
                _uiState.update {
                    it.copy(results = list, isLoading = false)
                }
            }
        }
    }

    fun addToShelf(book: BookEntity) {
        viewModelScope.launch {
            repository.addBook(
                title = book.title,
                author = book.author,
                rating = book.rating
            )
        }
    }
}

class LibraryViewModelFactory(
    private val repository: BookRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LibraryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LibraryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
    }
}