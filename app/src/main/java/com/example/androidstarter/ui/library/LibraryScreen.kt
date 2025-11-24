package com.example.androidstarter.ui.library

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidstarter.ui.components.BookCard
import com.example.androidstarter.Tag
import com.example.androidstarter.data.BookRepository
import com.example.androidstarter.data.local.AppDatabase
import com.example.androidstarter.toUiModel

@Composable
fun LibraryScreen() {
    val context = LocalContext.current
    val db = remember { AppDatabase.getInstance(context) }
    val repo = remember { BookRepository(db.bookDao()) }

    val vm: LibraryViewModel = viewModel(
        factory = LibraryViewModelFactory(repo)
    )

    val uiState by vm.uiState.collectAsState()
    val searchQuery = uiState.query
    val searchResults = uiState.results

    val hotTags = listOf(
        Tag("Science Fiction", 1247),
        Tag("Mystery", 892),
        Tag("Romance", 1156),
        Tag("Fantasy", 943),
        Tag("Thriller", 678),
        Tag("Biography", 445),
        Tag("History", 567),
        Tag("Self-Help", 723)
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Library",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { vm.onQueryChange(it) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search books, authors, or topics...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                },
                singleLine = true
            )
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Popular Tags",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(hotTags) { tag ->
                    TagChip(tag = tag)
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Search Results",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }

        items(searchResults) { entity ->
            BookCard(
                book = entity.toUiModel(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
        }

        item {
            if (searchQuery.isEmpty()) {
                Text(
                    text = "Enter a search term to find books",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(vertical = 32.dp)
                )
            } else {
                Text(
                    text = "Searching for: \"$searchQuery\"",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }
        }
    }
}

@Composable
fun TagChip(tag: Tag) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = tag.name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "(${tag.count})",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}