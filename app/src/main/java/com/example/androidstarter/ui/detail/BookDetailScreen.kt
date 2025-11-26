package com.example.androidstarter.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidstarter.data.BookRepository
import com.example.androidstarter.data.local.AppDatabase
import com.example.androidstarter.ui.components.BookCard
import com.example.androidstarter.toUiModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailScreen(
    bookId: Long,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val db = remember { AppDatabase.getInstance(context) }
    val repo = remember { BookRepository(db.bookDao()) }

    val vm: BookDetailViewModel = viewModel(
        factory = BookDetailViewModelFactory(repo, bookId)
    )

    val uiState by vm.uiState.collectAsState()
    val book = uiState.book

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Book detail") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator()
                }

                book == null -> {
                    Text("Book not found")
                }

                else -> {
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
            }
        }
    }
}