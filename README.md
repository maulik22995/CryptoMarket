# CryptoMarket

A **Kotlin Multiplatform** crypto market app built with **Compose Multiplatform**. Browse coins, track favourites, view details and charts, and search—all with a single shared codebase for Android, iOS, and Desktop.

> **Learning project** – Built for learning KMP and Compose Multiplatform; not intended for production use.

---

## Features

- **Market** – List of crypto coins with price and 24h change; sort by popular, newest, gainers, losers, or market cap
- **Favourites** – Save coins to a list; favourites stay in sync when you add/remove from other screens (reactive list)
- **Coin details** – Price, stats, and historical price chart with period selector (24h, 7d, 30d, 90d)
- **Search** – Find coins by name or symbol with live results
- **Offline cache** – Coins cached locally (Room) for faster load and basic offline use

---

## Tech stack

| Layer | Technologies |
|-------|--------------|
| **UI** | Compose Multiplatform, Material 3 |
| **Navigation** | Jetpack Navigation Compose |
| **DI** | Koin |
| **Networking** | Ktor (OkHttp on Android/Desktop, Darwin on iOS) |
| **Local DB** | Room (SQLite) |
| **Images** | Coil 3 |
| **Async** | Kotlin Coroutines, Flow |

---

## Project structure

```
CryptoMarket/
├── composeApp/                    # Shared KMP app
│   ├── src/
│   │   ├── commonMain/            # Shared code (UI, domain, data)
│   │   │   ├── kotlin/.../        # App package
│   │   │   │   ├── data/          # Repos, data sources, DB, mappers
│   │   │   │   ├── di/            # Koin modules
│   │   │   │   ├── domain/        # Use cases
│   │   │   │   ├── model/         # Domain models
│   │   │   │   ├── navigation/    # Nav routes & scaffold
│   │   │   │   └── ui/            # Screens, theme, components
│   │   │   └── composeResources/  # Drawables, strings
│   │   ├── androidMain/           # Android-specific (Application, DB driver, etc.)
│   │   ├── iosMain/               # iOS-specific
│   │   └── desktopMain/           # Desktop JVM entry
│   └── build.gradle.kts
├── iosApp/                        # iOS app (Xcode project, embeds Compose framework)
├── build.gradle.kts
├── settings.gradle.kts
└── gradle/
    └── libs.versions.toml
```

---

## Prerequisites

- **JDK 11+**
- **Android Studio** (or IDE with Android SDK) for Android
- **Xcode** (macOS) for iOS
- **Kotlin 2.1.x**

---

## How to run

### Android

```bash
./gradlew :composeApp:installDebug
```

Or open the project in Android Studio and run the `composeApp` Android configuration.

### iOS

1. Open `iosApp/iosApp.xcodeproj` in Xcode.
2. Select a simulator or device and run (⌘R).

From terminal (macOS):

```bash
./gradlew :composeApp:linkDebugFrameworkIosSimulatorArm64
# Then build & run from Xcode
```

### Desktop (JVM)

```bash
./gradlew :composeApp:run
```

---

## Data source & API key

Market and coin data is provided by [Coinranking API](https://developers.coinranking.com/). The API key is **not** stored in the repo. Configure it per platform:

| Platform | How to set the key |
|----------|---------------------|
| **Android** | Copy `local.properties.example` to `local.properties` and set `COINRANKING_API_KEY=your_key` (local.properties is gitignored) |
| **iOS** | Set environment variable `COINRANKING_API_KEY` in your Xcode scheme (Edit Scheme → Run → Arguments → Environment Variables) |
| **Desktop** | Set env var before running: `export COINRANKING_API_KEY=your_key` (or add to your shell profile) |

Get a free API key at [Coinranking developers](https://developers.coinranking.com/).

**If you previously committed an API key:** revoke/regenerate that key in the Coinranking dashboard so the old value can no longer be used.

---

## Demo videos

Watch the app in action on both platforms:

| Platform  | Video |
|-----------|-------|
| **Android** | [Watch Android demo](doc/android.mp4) |
| **iOS**     | [Watch iOS demo](doc/ios.mp4) |

---

## Learn more

- [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html)
- [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)
- [Coinranking API](https://developers.coinranking.com/)
