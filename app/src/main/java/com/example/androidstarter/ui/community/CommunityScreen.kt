package com.example.androidstarter.ui.community

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.androidstarter.Discussion
import com.example.androidstarter.ui.home.DiscussionCard

@Composable
fun CommunityScreen() {
    // 分类标签
    val categories = listOf("All", "Book Request", "Recommendation", "Discussion")
    var selectedCategory by remember { mutableStateOf("All") }

    // 初始示例帖子（可按需要改文案）
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

    // 发帖弹窗状态
    var showDialog by remember { mutableStateOf(false) }
    var newTitle by remember { mutableStateOf("") }
    var newContent by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // 主内容列表
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 标题 + 说明
            item {
                Text(
                    text = "Community",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Start discussions, share reviews, and see what others are reading.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // 分类 chips
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

            // 小标题
            item {
                Text(
                    text = "Latest discussions",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium
                )
            }

            // 帖子列表（这里没做真正过滤，作业原型够用）
            items(discussions) { discussion ->
                DiscussionCard(discussion = discussion)
            }

            item { Spacer(modifier = Modifier.height(80.dp)) }
        }

        // 右下角 New post 按钮（不再用 Scaffold，保证一定能看到）
        FloatingActionButton(
            onClick = { showDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            androidx.compose.material3.Icon(
                imageVector = Icons.Filled.Create,
                contentDescription = "New post"
            )
        }

        // 发帖弹窗
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
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
                                showDialog = false
                            }
                        }
                    ) {
                        Text("Post")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
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
}

@Composable
fun CategoryChip(
    category: String,
    isSelected: Boolean,
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