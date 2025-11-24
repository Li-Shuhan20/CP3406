package com.example.androidstarter.ui.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidstarter.data.BookRepository
import com.example.androidstarter.data.local.BookEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    fun onQueryChange(newQuery: String) {
        _uiState.value = _uiState.value.copy(
            query = newQuery,
            isLoading = true
        )

        viewModelScope.launch {
            repository.searchBooks(newQuery).collect { list ->
                _uiState.value = _uiState.value.copy(
                    results = list,
                    isLoading = false
                )
            }
        }
    }
}
