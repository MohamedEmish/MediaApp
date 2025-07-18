# 🎧 Pulse

A modern Android application built with **Jetpack Compose**, **Kotlin**, and a clean architecture approach. **Pulse** serves as a modular, scalable, and production-ready base for audio-focused content such as **Podcasts**, **Audio Articles**, **Audiobooks**, and **Episodes**.

---

## 🚀 Tech Stack

| Layer       | Tech & Tools |
|-------------|--------------|
| **Language**      | Kotlin 2.2.0 |
| **UI Toolkit**    | Jetpack Compose (BOM 2025.07.00) |
| **DI**            | Hilt 2.56.2 + Hilt Navigation Compose |
| **Networking**    | Retrofit 3.0.0 + LoggingInterceptor + Chucker |
| **Async / Flow**  | Kotlin Coroutines 1.10.2 |
| **Persistence**   | DataStore |
| **Image Loading** | Coil 2.7.0 |
| **Security**      | Google Tink 1.18.0 |
| **Serialization** | Gson + Kotlinx Serialization |
| **Testing**       | JUnit, Espresso, MockK, Turbine |

---

## 🧱 Architecture

**Modular Clean Architecture** with separation of concerns:
📦 Pulse/
├── app
    ├── ui                 # Presentation layer (screen and view models)
├── core 
    ├── data               # Data layer (repositories, remote APIs, local sources) implementations
        ├── dataSource 
            ├── local      # DataStore
            ├── remote     # API
            ├── di         # Hilt modules and providers
    ├── domai              # Business logic (use cases, models)
        ├── useCases 
        ├── appConstants 
        ├── utils         # General-purpose tools
    ├── ui # Jetpack Compose Base UI and ViewModels

- Follows **MVVM + Clean Architecture**
- **State management** using Kotlin `Flow` and `StateFlow`
- **Composable Navigation**
- Fully scalable and testable

---

## 🖼️ UI Features

- 🎙️ Browse Podcasts, AudioBooks, Episodes, and Audio Articles
- 🎨 Dynamic theming using Palette API
- 🔄 Lottie animations for smooth UI interactions

---

## 🧪 Testing

### Included Testing Stack

| Tool       | Usage |
|------------|-------|
| `JUnit` | Unit testing |
| `Espresso` | UI Testing |
| `MockK` | Mocking dependencies |
| `Turbine` | Flow testing |
| `core-testing` | LiveData / Architecture components |
| `kotlinx.coroutines.test` | Coroutine testing |

---

🧩 Modular & Scalable
The app is designed to support modularity and separation of concerns:

- Feature modules can be added easily (e.g., feature-auth, feature-profile)
- Shared UI and core utilities are reusable
- Each domain layer can be tested independently

---

👨‍💻 Author
Mohamed Emish

Senior Android Engineer

📍 Riyadh, Saudi Arabia

📧 Emish52020@gmail.com

