package com.example.androidstarter.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.platform.LocalContext
import com.example.androidstarter.data.BookRepository
import com.example.androidstarter.data.local.AppDatabase
import com.example.androidstarter.ui.components.BookCard
import com.example.androidstarter.toUiModel

@Composable
fun BookDetailScreen(
    bookId: Long
) {
    val context = LocalContext.current
    val db = remember { AppDatabase.getInstance(context) }
    val repo = remember { BookRepository(db.bookDao()) }

    val vm: BookDetailViewModel = viewModel(
        factory = BookDetailViewModelFactory(repo, bookId)
    )

    val uiState by vm.uiState.collectAsState()

    val book = uiState.book

    if (uiState.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    if (book == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Book not found")
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        BookCard(
            book = book.toUiModel(),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { vm.increaseProgress() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Read 10% more")
        }
    }
}