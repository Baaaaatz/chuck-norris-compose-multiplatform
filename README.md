# Chuck Norris Compose Multiplatform

A **Kotlin Multiplatform** starter app showcasing shared Compose UI across Android & iOS with clean architecture, DI, and testable ViewModels.

## 🚀 Features

- Shared UI logic implemented in `composeApp/commonMain`.
- Reusable **Composable** screens for Android and iOS.
- **ViewModel + StateFlow** backed UI states with auto-loading on first collect.
- **Dependency Injection** using Koin.
- **Testable architecture** with `DispatchersProvider` and mocking via Mokkery or MockK.

## 📦 Libraries Used

| Library                         | Purpose                                   |
|---------------------------------|-------------------------------------------|
| [Jetpack Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/) | Shared UI across Android and iOS         |
| [Koin](https://insert-koin.io/) | Dependency Injection for ViewModels and services |
| [Kotlinx Coroutines](https://github.com/Kotlin/kotlinx.coroutines) | Asynchronous programming & Flow handling |
| [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization) | JSON serialization                       |
| [Ktor](https://ktor.io/)        | HTTP client for API requests              |
| [Mokkery](https://github.com/mokkery/mokkery) | Unit testing mocks                        |
| [Turbine](https://github.com/cashapp/turbine) | Flow testing utilities                    |

## 🧱 ComposeApp Module

- Hosts native implementation of UI and ViewModel in a **single shared module**.
- Exposes fully usable Composable screens for **both Android and iOS hosts**.
- You can integrate this module in any native Kotlin/iOS project:
  ```kotlin
  // e.g., in a SwiftUI ViewController or Android Activity:
  setContent {
    ChuckNorrisAppTheme {
      CategoriesScreen(/* ... */)
    }
  }
  
