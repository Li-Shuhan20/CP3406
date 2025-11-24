package com.example.androidstarter

import com.example.androidstarter.data.local.BookEntity

data class BookUiModel(
    val id: Long = 0L,
    val title: String,
    val author: String,
    val rating: Float = 0f,
    val progress: Float = 0f
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

fun BookEntity.toUiModel(): BookUiModel =
    BookUiModel (
        id = id,
        title = title,
        author = author,
        rating = rating,
        progress = progress
    )