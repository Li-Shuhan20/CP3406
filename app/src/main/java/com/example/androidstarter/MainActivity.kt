package com.example.androidstarter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    Shelf("shelf", "书架"),
    Library("library", "图书馆"),
    Home("home", "主页"),
    Community("community", "社区"),
    Profile("profile", "我的")
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
            composable(Dest.Shelf.route) { CenteredText("书架") }
            composable(Dest.Library.route) { CenteredText("图书馆") }
            composable(Dest.Home.route) { CenteredText("主页") }
            composable(Dest.Community.route) { CenteredText("社区") }
            composable(Dest.Profile.route) { CenteredText("我的") }
        }
    }
}

@Composable
private fun CenteredText(text: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = text, style = MaterialTheme.typography.titleLarge)
    }
}

