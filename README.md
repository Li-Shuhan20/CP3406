# My Reading Shelf

My Reading Shelf is a simple reading tracker app built for **CP3406 Assignment 2**.  
It helps casual readers track the books they are reading, update their reading progress, and browse a small community and profile section.

The app is written in **Kotlin** using **Jetpack Compose** and modern Android APIs. It uses **Room** for local storage and a **book search API** for the Library screen. The app follows an MVVM architecture with ViewModels and a Repository.

---

## Core Features

### Home
- Welcome header for the user.
- **Continue Reading** section that shows books from the local shelf with progress between 0% and 100%.
- A small **recommendation / community preview** area so the home screen does not feel empty.
- Tapping a book in *Continue Reading* opens the book detail screen.

### Library
- Search bar for searching books by title, author, or topic.
- Uses a **remote Open Library API** to get book results.
- Results are displayed as book cards.
- Books that are already in the local shelf can be opened from here and viewed in the detail screen.
- Shows popular tags as simple chips (e.g. Science Fiction, Fantasy, Romance).

### Shelf
- Shows all books stored in the **Room** database.
- Each book card displays title, author, rating and a progress bar.
- Tapping a book opens the **Book Detail** screen.

### Book Detail
- Shows full information for a single book.
- Displays the current reading progress (0.0 to 1.0).
- A button labelled **“Read 10% more”** increases the progress in Room and updates the UI.
- Uses a top app bar with a back button to return to the previous screen.
- Handles loading state and shows a “Book not found” message if the ID is invalid.

### Community
- Shows a list of simple discussion cards with title, author, reply count and likes.
- Uses Compose layouts, icons and cards.
- Data is mock data, but the layout demonstrates a possible community feature.

### Profile
- Displays a basic user profile header (name and avatar placeholder).
- Simple settings-style items such as account, reading goals, and notifications.
- Demonstrates another common mobile layout and completes the bottom navigation.

---

## Tech Stack

- **Language:** Kotlin
- **UI:** Jetpack Compose, Material 3
- **Architecture:** MVVM, Repository pattern, ViewModel + StateFlow
- **Local storage:** Room (AppDatabase, BookDao, BookEntity)
- **Networking:** Open Library API (Retrofit interface)
- **Navigation:** Navigation Compose, argument passing for `bookId`
- **Testing:** Basic unit and UI tests (for progress logic and main screens)
- **Version control:** Git + GitHub

---

## Architecture Overview

The app follows a simple MVVM structure:

- **Data layer**
  - `BookEntity` – Room entity representing a book.
  - `BookDao` – DAO with queries for shelf, continue reading, and book by ID.
  - `AppDatabase` – Room database singleton.
  - `BookRepository` – Single source of truth combining Room data and remote search results.

- **Domain / UI state**
  - `HomeUiState`, `ShelfUiState`, `LibraryUiState`, `BookDetailUiState` hold screen state.
  - `BookUiModel` is used to show books in the UI.

- **Presentation**
  - `HomeViewModel`, `ShelfViewModel`, `LibraryViewModel`, `BookDetailViewModel` expose StateFlows.
  - Screen composables (`HomeScreen`, `ShelfScreen`, `LibraryScreen`, `BookDetailScreen`, `CommunityScreen`, `ProfileScreen`) call `collectAsState()` to observe state.

---

## How to Run

1. Clone the repository.
2. Open it in **Android Studio** (Giraffe or newer).
3. Let Gradle sync finish.
4. Create an Android emulator or connect a device.
5. Run the app from Android Studio.

---

## Future Improvements

- Real user login and cloud sync.
- Stronger recommendation logic based on reading history and ratings.
- Full community features (posts, comments, likes, user profiles).
- Notifications and daily reading goals.
- Dark mode and more theme customisation.