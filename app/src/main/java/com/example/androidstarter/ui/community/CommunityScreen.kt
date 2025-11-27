package com.example.androidstarter.ui.community

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.androidstarter.Discussion
import com.example.androidstarter.ui.home.DiscussionCard

@Composable
fun CommunityScreen() {
    // 分类（简单展示用）
    val categories = listOf("All", "Book Request", "Recommendation", "Discussion")
    var selectedCategory by remember { mutableStateOf("All") }

    // 初始示例帖子
    val discussions = remember {
        mutableStateListOf(
            Discussion(
                title = "Looking for books similar to 'The Seven Husbands of Evelyn Hugo'",
                author = "RomanceReader",
                replies = 45,
                likes = 23,
                category = "Book Request",
                timeAgo = "1h ago"
            ),
            Discussion(
                title = "Just finished 'Project Hail Mary' - absolutely mind-blowing!",
                author = "SciFiLover",
                replies = 89,
                likes = 67,
                category = "Recommendation",
                timeAgo = "3h ago"
            ),
            Discussion(
                title = "Anyone else cry reading 'The Little Prince' as an adult?",
                author = "NostalgicReader",
                replies = 64,
                likes = 41,
                category = "Discussion",
                timeAgo = "5h ago"
            )
        )
    }

    // 发帖对话框状态
    var showNewPostDialog by remember { mutableStateOf(false) }
    var newTitle by remember { mutableStateOf("") }
    var newContent by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 标题 + 右上角 New post 按钮
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Community",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Start discussions, share reviews, and see what others are reading.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                FilledTonalButton(onClick = { showNewPostDialog = true }) {
                    Icon(
                        imageVector = Icons.Filled.Create,
                        contentDescription = "New post",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("New post")
                }
            }
        }

        // 分类 Chips
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(categories) { category ->
                    CategoryChip(
                        category = category,
                        isSelected = category == selectedCategory,
                        onClick = { selectedCategory = category }
                    )
                }
            }
        }

        // 分隔标题
        item {
            Text(
                text = "Latest discussions",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium
            )
        }

        items(discussions) { discussion ->
            DiscussionCard(discussion = discussion)
        }

        item { Spacer(modifier = Modifier.height(32.dp)) }
    }

    if (showNewPostDialog) {
        AlertDialog(
            onDismissRequest = { showNewPostDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (newTitle.isNotBlank()) {
                            discussions.add(
                                0,
                                Discussion(
                                    title = newTitle.trim(),
                                    author = "You",
                                    replies = 0,
                                    likes = 0,
                                    category = "Discussion",
                                    timeAgo = "Just now"
                                )
                            )
                            newTitle = ""
                            newContent = ""
                            showNewPostDialog = false
                        }
                    }
                ) {
                    Text("Post")
                }
            },
            dismissButton = {
                TextButton(onClick = { showNewPostDialog = false }) {
                    Text("Cancel")
                }
            },
            title = { Text("New discussion") },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = newTitle,
                        onValueChange = { newTitle = it },
                        label = { Text("Title") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = newContent,
                        onValueChange = { newContent = it },
                        label = { Text("What do you want to talk about?") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        maxLines = 4
                    )
                    Text(
                        text = "Posts are stored locally on this device for this prototype.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        )
    }
}

@Composable
fun CategoryChip(
    category: String,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Text(
            text = category,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            color = if (isSelected)
                MaterialTheme.colorScheme.onPrimary
            else
                MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}