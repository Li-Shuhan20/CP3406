package com.example.androidstarter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.androidstarter.ui.library.LibraryScreen
import com.example.androidstarter.ui.profile.ProfileScreen
import com.example.androidstarter.ui.shelf.ShelfScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { 
            WelcomeScreen()
        }
    }
}

enum class Dest(val route: String, val label: String) {
    Shelf("shelf", "Shelf"),
    Library("library", "Library"),
    Home("home", "Home"),
    Community("community", "Community"),
    Profile("profile", "Profile")
}

@Composable
fun AppRoot() {
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
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
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

@Composable
fun WelcomeScreen() {
    var showWelcome by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(2000)
        showWelcome = false
    }

    if (showWelcome) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Welcome",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "User",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    } else {
        AppRoot()
    }
}
