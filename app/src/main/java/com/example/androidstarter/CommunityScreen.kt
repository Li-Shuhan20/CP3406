package com.example.androidstarter

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun CommunityScreen() {
    val communityPosts = listOf(
        Discussion("Looking for books similar to 'The Seven Husbands of Evelyn Hugo'", "RomanceReader", 45, 23, "Book Request", "1h ago"),
        Discussion("Just finished 'Project Hail Mary' - absolutely mind-blowing!", "SciFiLover", 89, 67, "Recommendation", "3h ago"),
        Discussion("What are your thoughts on the ending of 'The Midnight Library'?", "BookClubMember", 156, 98, "Discussion", "2h ago"),
        Discussion("Need recommendations for beginner fantasy books", "NewToFantasy", 72, 34, "Book Request", "5h ago"),
        Discussion("Has anyone read 'The Silent Patient'? Is it worth the hype?", "MysteryFan", 203, 145, "Discussion", "1h ago"),
        Discussion("Strongly recommend 'Educated' by Tara Westover - life-changing!", "MemoirReader", 124, 89, "Recommendation", "4h ago"),
        Discussion("Looking for books about time travel and parallel universes", "TimeTraveler", 38, 19, "Book Request", "6h ago"),
        Discussion("Book club discussion: 'The Handmaid's Tale' - thoughts?", "BookClubHost", 267, 178, "Discussion", "2h ago"),
        Discussion("Just discovered 'The Thursday Murder Club' series - amazing!", "CozyMysteryFan", 91, 56, "Recommendation", "3h ago"),
        Discussion("Need help choosing between these 3 sci-fi books", "IndecisiveReader", 67, 31, "Book Request", "7h ago")
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Community",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            val categories = listOf("All", "Book Request", "Recommendation", "Discussion")
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(categories) { category ->
                    CategoryChip(category = category, isSelected = category == "All")
                }
            }
        }

        items(communityPosts) { post ->
            DiscussionCard(discussion = post)
        }
    }
}

@Composable
fun CategoryChip(category: String, isSelected: Boolean = false) {
    Card(
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