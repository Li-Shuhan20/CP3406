package com.example.androidstarter

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidstarter.data.BookRepository
import com.example.androidstarter.data.local.AppDatabase
import com.example.androidstarter.ui.components.BookCard
import com.example.androidstarter.ui.home.HomeViewModel
import com.example.androidstarter.ui.home.HomeViewModelFactory

@Composable
fun HomeScreen() {
    val recommendedBooks = listOf(
        BookUiModel(
            title = "The Three-Body Problem",
            author = "Liu Cixin",
            rating = 4.8f,
        ),
        BookUiModel(
            title = "To Live",
            author = "Yu Hua",
            rating = 4.9f,
        ),
        BookUiModel(
            title = "One Hundred Years of Solitude",
            author = "Gabriel García Márquez",
            rating = 4.7f,
        ),
        BookUiModel(
            title = "1984",
            author = "George Orwell",
            rating = 4.6f,
        ),
        BookUiModel(
            title = "The Little Prince",
            author = "Antoine de Saint-Exupéry",
            rating = 4.9f,
        )
    )

    val hotDiscussions = listOf(
        Discussion(
            title = "What's the most shocking scene in 'The Three-Body Problem'?",
            author = "SciFi Fan",
            replies = 128,
            likes = 89,
            category = "Discussion",
            timeAgo = "2h ago"
        ),
        Discussion(
            title = "Recommend some good mystery novels",
            author = "Bookworm",
            replies = 76,
            likes = 45,
            category = "Recommendation",
            timeAgo = "5h ago"
        ),
        Discussion(
            title = "How to develop reading habits?",
            author = "Reading Expert",
            replies = 203,
            likes = 156,
            category = "Discussion",
            timeAgo = "1d ago"
        ),
        Discussion(
            title = "Most anticipated books of 2024",
            author = "Editor",
            replies = 94,
            likes = 67,
            category = "Discussion",
            timeAgo = "3h ago"
        ),
        Discussion(
            title = "E-books vs Physical books, which do you prefer?",
            author = "Book Lover",
            replies = 89,
            likes = 52,
            category = "Discussion",
            timeAgo = "6h ago"
        )
    )

    val context = LocalContext.current
    val db = remember { AppDatabase.getInstance(context) }
    val repo = remember { BookRepository(db.bookDao()) }

    val homeVm: HomeViewModel = viewModel(
        factory = HomeViewModelFactory(repo)
    )
    val uiState by homeVm.uiState.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Recommended Books",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(recommendedBooks) { book ->
                    BookCard(book = book)
                }
            }
        }

        if (uiState.continueReading.isNotEmpty()) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Continue reading",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            items(uiState.continueReading) { book ->
                BookCard(
                    book = book,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Hot Discussions",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }

        items(hotDiscussions) { discussion ->
            DiscussionCard(discussion = discussion)
        }
    }
}

@Composable
fun DiscussionCard(discussion: Discussion) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Card(
                modifier = Modifier.width(120.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Text(
                    text = discussion.category,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Text(
                text = discussion.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "by ${discussion.author} • ${discussion.timeAgo}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Chat,
                            contentDescription = "Replies",
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = discussion.replies.toString(),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ThumbUp,
                            contentDescription = "Likes",
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = discussion.likes.toString(),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}
