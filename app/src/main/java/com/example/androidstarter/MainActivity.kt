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
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
            composable(Dest.Shelf.route) { ShelfScreen() }
            composable(Dest.Library.route) { LibraryScreen() }
            composable(Dest.Home.route) { HomeScreen() }
            composable(Dest.Community.route) { CommunityScreen() }
            composable(Dest.Profile.route) { ProfileScreen() }
        }
    }
}

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
        // Recommended Books Section
        item {
            Text(
                text = "Recommended Books",
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
private fun BookCard(
    book: Book,
    modifier: Modifier = Modifier.width(140.dp)
) {
    Card(
        modifier = modifier,
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
            
            // Reading Progress
            if (book.progress > 0f) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Progress",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "${(book.progress * 100).toInt()}%",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    LinearProgressIndicator(
                        progress = book.progress,
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                }
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
            // Category Badge
            Card(
                modifier = Modifier.width(100.dp),
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
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
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

@Composable
private fun LibraryScreen() {
    var searchQuery by remember { mutableStateOf("") }
    
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
        // Search Bar Section
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
                onValueChange = { searchQuery = it },
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
        
        // Hot Tags Section
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Popular Tags",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }
        
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(hotTags) { tag ->
                    TagChip(tag = tag)
                }
            }
        }
        
        // Search Results Placeholder
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Search Results",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
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
private fun TagChip(tag: Tag) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
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

@Composable
private fun ShelfScreen() {
    val myBooks = listOf(
        Book("The Great Gatsby", "F. Scott Fitzgerald", 4.2f, 0.75f),
        Book("To Kill a Mockingbird", "Harper Lee", 4.8f, 1.0f),
        Book("Pride and Prejudice", "Jane Austen", 4.6f, 0.45f),
        Book("The Catcher in the Rye", "J.D. Salinger", 4.0f, 0.0f),
        Book("1984", "George Orwell", 4.6f, 0.9f),
        Book("Harry Potter and the Sorcerer's Stone", "J.K. Rowling", 4.9f, 1.0f),
        Book("The Lord of the Rings", "J.R.R. Tolkien", 4.7f, 0.6f),
        Book("Dune", "Frank Herbert", 4.3f, 0.25f),
        Book("The Hobbit", "J.R.R. Tolkien", 4.5f, 0.8f),
        Book("Brave New World", "Aldous Huxley", 4.1f, 0.0f)
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        item {
            Text(
                text = "My Shelf",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }
        
        // Books Grid
        items(myBooks.chunked(2)) { rowBooks ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                rowBooks.forEach { book ->
                    BookCard(
                        book = book,
                        modifier = Modifier.weight(1f)
                    )
                }
                // Fill empty space if odd number of books
                if (rowBooks.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun CommunityScreen() {
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
        // Header
        item {
            Text(
                text = "Community",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }
        
        // Category Filter
        item {
            val categories = listOf("All", "Book Request", "Recommendation", "Discussion")
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { category ->
                    CategoryChip(category = category, isSelected = category == "All")
                }
            }
        }
        
        // Community Posts
        items(communityPosts) { post ->
            DiscussionCard(discussion = post)
        }
    }
}

@Composable
private fun CategoryChip(category: String, isSelected: Boolean = false) {
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

@Composable
private fun ProfileScreen() {
    val profileItems = listOf(
        ProfileItem("Reading Stats", "View your reading statistics", Icons.Default.Timer),
        ProfileItem("Create Content", "Write reviews and share thoughts", Icons.Default.Create),
        ProfileItem("Book Collections", "Organize your favorite books", Icons.Default.Bookmarks),
        ProfileItem("Reading Goals", "Set and track reading targets", Icons.Default.Star)
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        item {
            Text(
                text = "Profile",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }
        
        // Profile Header Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Avatar
                    Card(
                        modifier = Modifier.size(80.dp),
                        shape = androidx.compose.foundation.shape.CircleShape,
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Profile Avatar",
                                modifier = Modifier.size(40.dp),
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                    
                    // User Info
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Book Lover",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "book.lover@example.com",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    
                    // Reading Stats
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        StatItem("Books Read", "47")
                        StatItem("Reading Time", "156h")
                        StatItem("Reviews", "23")
                    }
                }
            }
        }
        
        // Profile Items
        item {
            Text(
                text = "Creative Center",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
        
        items(profileItems) { item ->
            ProfileItemCard(item = item)
        }
    }
}

@Composable
private fun StatItem(label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun ProfileItemCard(item: ProfileItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.title,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = item.subtitle,
                    style = MaterialTheme.typography.bodyMedium,
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

