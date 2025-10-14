package com.example.androidstarter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { AppRoot() }
    }
}

private enum class Dest(val route: String, val label: String) {
    Shelf("shelf", "Shelf"),
    Library("library", "Library"),
    Home("home", "Home"),
    Community("community", "Community"),
    Profile("profile", "Profile")
}

@Composable
private fun AppRoot() {
    val navController = rememberNavController()
    val items = listOf(Dest.Shelf, Dest.Library, Dest.Home, Dest.Community, Dest.Profile)

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                items.forEach { dest ->
                    val selected = currentRoute == dest.route
                    val icon = when (dest) {
                        Dest.Shelf -> Icons.Default.Bookmarks
                        Dest.Library -> Icons.Default.Explore
                        Dest.Home -> Icons.Default.Home
                        Dest.Community -> Icons.Default.Book
                        Dest.Profile -> Icons.Default.Person
                    }
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            navController.navigate(dest.route) {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(icon, contentDescription = dest.label) },
                        label = { Text(dest.label) }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Dest.Home.route,
            modifier = Modifier.fillMaxSize()
        ) {
            composable(Dest.Shelf.route) { CenteredText("Shelf") }
            composable(Dest.Library.route) { CenteredText("Library") }
            composable(Dest.Home.route) { HomeScreen() }
            composable(Dest.Community.route) { CenteredText("Community") }
            composable(Dest.Profile.route) { CenteredText("Profile") }
        }
    }
}

// 数据类
data class Book(
    val title: String,
    val author: String,
    val rating: Float,
)

data class Discussion(
    val title: String,
    val author: String,
)

@Composable
private fun HomeScreen() {
    val recommendedBooks = listOf(
        Book("The Three-Body Problem", "Liu Cixin", 4.8f),
        Book("To Live", "Yu Hua", 4.9f),
        Book("One Hundred Years of Solitude", "Gabriel García Márquez", 4.7f),
        Book("1984", "George Orwell", 4.6f),
        Book("The Little Prince", "Antoine de Saint-Exupéry", 4.9f)
    )
    
    val hotDiscussions = listOf(
        Discussion("What's the most shocking scene in 'The Three-Body Problem'?", "SciFi Fan"),
        Discussion("Recommend some good mystery novels", "Bookworm"),
        Discussion("How to develop reading habits?", "Reading Expert"),
        Discussion("Most anticipated books of 2024", "Editor"),
        Discussion("E-books vs Physical books, which do you prefer?", "Book Lover")
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Recommended Books Section
        item {
            Text(
                text = "📚 Recommended Books",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }
        
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(recommendedBooks) { book ->
                    BookCard(book = book)
                }
            }
        }
        
        // Hot Community Discussions Section
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "🔥 Hot Discussions",
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
private fun BookCard(book: Book) {
    Card(
        modifier = Modifier.width(140.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "📖",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = book.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = book.author,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Rating",
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = book.rating.toString(),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
private fun DiscussionCard(discussion: Discussion) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
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
                    text = "by ${discussion.author}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun CenteredText(text: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = text, style = MaterialTheme.typography.titleLarge)
    }
}

