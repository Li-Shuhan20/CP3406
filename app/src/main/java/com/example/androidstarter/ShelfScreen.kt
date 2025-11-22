package com.example.androidstarter

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ShelfScreen() {
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
        item {
            Text(
                text = "My Shelf",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }

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
                if (rowBooks.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}