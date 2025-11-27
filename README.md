# VERSE VAULT

VERSE VAULT is a mobile reading tracker app developed for **CP3406 Assessment 2**.  
It allows users to search for books, save them to a local shelf, track reading progress, rate and review books, view reading statistics, and browse a simple community feed.

The app is built with **Kotlin**, **Jetpack Compose**, **Room**, and **MVVM architecture**, and integrates the **Open Library API** for online book search.  
It demonstrates a complete multi-screen mobile application with state management, persistent storage, navigation, and UI testing.

---

## ⭐ Core Features

### 🏠 Home
- Displays recommended books (static mock content).
- **Continue Reading section** showing books in progress from the Room database.
- Tapping a book opens the **Book Detail** screen.

### 🔍 Library (Online Search)
- Users can search for books by title, author, or keywords.
- Uses the **Open Library API** to fetch results.
- If a book is already in the shelf, tapping opens its detail page.
- Books not in the shelf show an **Add to shelf** button that inserts the book into Room.
- Includes simple “Popular Tags” chip suggestions.

### 📚 Shelf
- Displays all locally saved books from Room.
- Shows title, author, rating, and a progress bar.
- Fully interactive — tapping opens the detail page.

### 📖 Book Detail
- Shows the complete information for a book stored in Room.
- **Update reading progress**:
    - *Quick +10%* button
    - *Set exact progress* dialog with slider
- **Rating slider (0–5)** with proper rounding.
- **Short review / notes** text field.
- All updates are immediately saved into Room.

### 💬 Community
- Simple, static discussion feed (mock data).
- Users can create new posts via the **New Post** floating button.
- New posts appear instantly at the top of the discussion list.
- Used to demonstrate layout, cards, lists, and dialogs.

### 👤 Profile
- Profile header with icon and placeholder email.
- **Reading statistics** (powered by Room):
    - Total books
    - Finished books
    - In-progress books
- **Reading Goals Dashboard**:
    - User can set a yearly reading target.
    - A progress bar shows `finishedBooks / goal`.
    - Automatically updates as books are completed.
- Simple dashboard items (Create Content, Collections, etc.) for layout demonstration.

---

## 🏗️ Tech Stack

- **Kotlin**
- **Jetpack Compose** (Material 3)
- **Navigation Compose**
- **Room Database**
- **Open Library API** (Retrofit-style interface)
- **MVVM + Repository**
- **StateFlow + collectAsState()**
- **Git & GitHub**
- **Basic Unit Tests + Compose UI Test**

---

## 📐 Architecture Overview

The project uses a clean MVVM structure.

### Data Layer
- `BookEntity` — Room data model
- `BookDao` — queries for shelf, continue reading, progress, rating, stats
- `AppDatabase` — Room singleton
- `BookRepository` — manages Room + remote search (Open Library)

### UI State
- Screen state models:  
  `HomeUiState`, `LibraryUiState`, `ShelfUiState`,  
  `BookDetailUiState`, `ProfileUiState`
- `BookUiModel` maps Room entities into UI-ready models.

### Presentation Layer
- ViewModels for each screen, exposing `StateFlow`
- Composable screens (`HomeScreen`, `LibraryScreen`, `ShelfScreen`, `BookDetailScreen`, `CommunityScreen`, `ProfileScreen`)
- `MainActivity` hosts the navigation graph and bottom navigation bar.

---

## 🧪 Testing

### Unit Tests
- **ModelsTest**  
  Tests mapping from `BookEntity` → `BookUiModel`.
- **ReadingStatsTest**  
  Tests correctness of reading statistics structure.

### UI Test (Instrumented)
- **CommunityScreenTest**  
  Verifies that the “Community” header and “Latest discussions” text appear correctly.

---

## ▶️ How to Run

1. Open the project in Android Studio (Giraffe or newer).
2. Wait for Gradle sync to finish.
3. Run the app on an emulator or device.
4. To run tests:  
   **Run > Run Tests** or use the gutter icons beside test classes.

---

## 🚀 Future Improvements

- Cloud sync + user accounts
- More advanced book recommendation logic based on reading patterns
- Full community backend with comments and real data
- More UI tests and repository-level tests
- Dark mode & accessibility improvements
- Persistent storage for reading goals (DataStore)