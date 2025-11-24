package com.example.androidstarter.ui.shelf

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidstarter.ui.components.BookCard
import com.example.androidstarter.data.BookRepository
import com.example.androidstarter.data.local.AppDatabase
import com.example.androidstarter.toUiModel

@Composable
fun ShelfScreen() {
    val context = LocalContext.current
    val db = remember { AppDatabase.getInstance(context) }
    val repo = remember { BookRepository(db.bookDao()) }

    val vm: ShelfViewModel = viewModel(
        factory = ShelfViewModelFactory(repo)
    )

    val uiState by vm.uiState.collectAsState()

    val myBooks = uiState.books.map { it.toUiModel() }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "My Shelf",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }

        items(myBooks.chunked(2)) { rowBooks ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                rowBooks.forEach { bookUiModel ->
                    BookCard(
                        book = bookUiModel,
                        modifier = Modifier.weight(1f)
                    )
                }

                if (rowBooks.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}