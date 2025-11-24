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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.androidstarter.ui.components.BookCard

@Composable
fun HomeScreen() {
    val recommendedBooks = listOf(
        BookUiModel(
            id = 0L,
            title = "The Three-Body Problem",
            author = "Liu Cixin",
            rating = 4.8f,
            progress = 0f
        ),
        BookUiModel(
            id = 0L,
            title = "To Live",
            author = "Yu Hua",
            rating = 4.9f,
            progress = 0f
        ),
        BookUiModel(
            id = 0L,
            title = "One Hundred Years of Solitude",
            author = "Gabriel García Márquez",
            rating = 4.7f,
            progress = 0f
        ),
        BookUiModel(
            id = 0L,
            title = "1984",
            author = "George Orwell",
            rating = 4.6f,
            progress = 0f
        ),
        BookUiModel(
            id = 0L,
            title = "The Little Prince",
            author = "Antoine de Saint-Exupéry",
            rating = 4.9f,
            progress = 0f
        )
    )

    val hotDiscussions = listOf(
        Discussion("What's the most shocking scene in 'The Three-Body Problem'?", "SciFi Fan", 128, 89, "Discussion", "2h ago"),
        Discussion("Recommend some good mystery novels", "Bookworm", 76, 45, "Recommendation", "5h ago"),
        Discussion("How to develop reading habits?", "Reading Expert", 203, 156, "Discussion", "1d ago"),
        Discussion("Most anticipated books of 2024", "Editor", 94, 67, "Discussion", "3h ago"),
        Discussion("E-books vs Physical books, which do you prefer?", "Book Lover", 89, 52, "Discussion", "6h ago")
    )

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
