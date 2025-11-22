package com.example.androidstarter

import androidx.compose.ui.graphics.vector.ImageVector

// 数据类
data class Book(
    val title: String,
    val author: String,
    val rating: Float,
    val progress: Float = 0f // Reading progress (0.0 to 1.0)
)

data class Discussion(
    val title: String,
    val author: String,
    val replies: Int,
    val likes: Int,
    val category: String,
    val timeAgo: String
)

data class Tag(
    val name: String,
    val count: Int
)

data class ProfileItem(
    val title: String,
    val subtitle: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)