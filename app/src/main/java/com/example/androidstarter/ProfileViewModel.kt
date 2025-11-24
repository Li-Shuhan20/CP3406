package com.example.androidstarter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.androidstarter.data.BookRepository
import com.example.androidstarter.data.ReadingStats
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ProfileUiState(
    val totalBooks: Int = 0,
    val finishedBooks: Int = 0,
    val inProgressBooks: Int = 0,
    val isLoading: Boolean = true
)

class ProfileViewModel(
    private val repository: BookRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        refreshStats()
    }

    fun refreshStats() {
        viewModelScope.launch {
            val stats: ReadingStats = repository.getReadingStats()
            _uiState.value = ProfileUiState(
                totalBooks = stats.total,
                finishedBooks = stats.finished,
                inProgressBooks = stats.inProgress,
                isLoading = false
            )
        }
    }
}

class ProfileViewModelFactory(
    private val repository: BookRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


