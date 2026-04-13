# AuthJourney - Modern Firebase Authentication App

AuthJourney is a professional, sleek Android application built to demonstrate modern development practices using **Jetpack Compose** and **Firebase Authentication**. It features a "Journey" themed UI with custom gradients, type-safe navigation, and robust state management.

## 🚀 Features

* **User Sign Up**: Create a new account with a Full Name (Username), Email, and Password.
* **User Login**: Secure authentication for existing users.
* **Dynamic Home Screen**: A personalized landing page that greets the user by their display name after a successful login.
* **Real-time Validation**: Instant feedback for empty fields or authentication errors using a reactive UI state.
* **Themed UI**: Consistent "Journey" aesthetic using custom purple/blue gradients and Material3 components.

## 🛠 Tech Stack

* **Language**: [Kotlin]
* **UI Framework**: [Jetpack Compose] (Material3)
* **Backend**: [Firebase Authentication]
* **Dependency Injection**: [Hilt]
* **Navigation**: [Jetpack Compose Navigation](Type-Safe Routes)
* **Architecture**: MVVM (Model-View-ViewModel)
* **State Management**: 
    * `MutableStateFlow` for reactive data handling.
    * `Sealed Classes` for explicit UI State management (Idle, Loading, Success, Error).
* **Asynchronous Programming**: [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) & Flow.

## 🏗 Project Architecture

The project follows Clean Architecture principles:
* **`di`**: Hilt modules providing Firebase instances.
* **`navigation`**: Type-safe navigation routes using Kotlin Serialization.
* **`ui.feature`**: UI screens and their respective ViewModels (Sign Up, Login, Home).
* **`ui.theme`**: Custom Material3 theme with gradient implementations.

## ⚙️ Setup Instructions

1.  **Clone the repository**:
    ```bash
    git clone [https://github.com/jeet030106/Auth_Service.git](https://github.com/jeet030106/Auth_Service.git)
    ```
2.  **Firebase Configuration**:
    * Create a project in the [Firebase Console](https://console.firebase.google.com/).
    * Add an Android App with your package name (`com.example.auth`).
    * Download the `google-services.json` file and place it in the `app/` directory.
    * Enable **Email/Password** authentication in the Firebase Auth settings.
3.  **Build and Run**:
    * Open the project in Android Studio (Ladybug or newer recommended).
    * Sync Gradle and run the app on an emulator or physical device.

## 📝 License

This project is licensed under the MIT License.
