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

    var showProgressDialog by remember { mutableStateOf(false) }

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
                    var rating by remember(book.id) {
                        mutableStateOf(book.rating.coerceIn(0f, 5f))
                    }
                    var review by remember(book.id) {
                        mutableStateOf(book.review)
                    }
                    var progressForDialog by remember(book.id) {
                        mutableStateOf(book.progress.coerceIn(0f, 1f))
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

                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            val percent = (book.progress * 100).toInt()
                            Text(
                                text = "Reading progress: $percent%",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Button(onClick = { vm.increaseProgress() }) {
                                    Text("Quick +10%")
                                }
                                OutlinedButton(
                                    onClick = {
                                        progressForDialog = book.progress.coerceIn(0f, 1f)
                                        showProgressDialog = true
                                    }
                                ) {
                                    Text("Set exact progress")
                                }
                            }
                        }

                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Your rating",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Slider(
                                value = rating,
                                onValueChange = { rating = it },
                                valueRange = 0f..5f,
                                steps = 9
                            )
                            Text(
                                text = String.format("Rating: %.1f / 5", rating),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedTextField(
                                value = review,
                                onValueChange = { review = it },
                                modifier = Modifier.fillMaxWidth(),
                                label = { Text("Your notes / review") },
                                maxLines = 4
                            )
                            Button(
                                onClick = { vm.updateRatingAndReview(rating, review) },
                                modifier = Modifier.align(Alignment.End)
                            ) {
                                Text("Save review")
                            }
                        }
                    }

                    if (showProgressDialog) {
                        AlertDialog(
                            onDismissRequest = { showProgressDialog = false },
                            confirmButton = {
                                TextButton(
                                    onClick = {
                                        vm.setProgress(progressForDialog)
                                        showProgressDialog = false
                                    }
                                ) {
                                    Text("Save")
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = { showProgressDialog = false }) {
                                    Text("Cancel")
                                }
                            },
                            title = { Text("Set exact progress") },
                            text = {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    val percent = (progressForDialog * 100).toInt()
                                    Text("Select your reading progress: $percent%")
                                    Slider(
                                        value = progressForDialog,
                                        onValueChange = { progressForDialog = it },
                                        valueRange = 0f..1f
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}