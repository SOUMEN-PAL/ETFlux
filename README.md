# ETFlux – Combined Documentation

This file aggregates all Markdown documents in a single place for easy consumption.

Table of Contents
- 1. Architecture
- 2. Modules
- 3. Build and Run
- 4. Navigation
- 5. Data and Persistence
- 6. DI and Configuration
- 7. Code Style and Conventions
- 8. Contributing
- 9. Troubleshooting
- 10. FAQ

---


# ETFlux

ETFlux is a modular Android application built with Kotlin and Jetpack Compose that provides market data browsing, watchlist management, and ticker insights. The project demonstrates a clean modular architecture, dependency injection with Koin, persistence with Room, and navigation with the Jetpack Compose Navigation component.

This README provides a quick overview. Extensive documentation is available in the docs/ directory:

- docs/Architecture.md
- docs/Modules.md
- docs/BuildAndRun.md
- docs/Navigation.md
- docs/DataAndPersistence.md
- docs/DIAndConfiguration.md
- docs/CodeStyleAndConventions.md
- docs/Contributing.md
- docs/Troubleshooting.md
- docs/FAQ.md

## Quick Start

- Requirements: Android Studio Iguana or newer, JDK 17, Android SDK 24+.
- Clone the repository and open it in Android Studio.
- Build and run the app on a device or emulator. See docs/BuildAndRun.md for details.

## High-level Architecture

```
+------------------------+       +------------------------+
|        app             |       |       shared           |
| - Application, DI boot |<----->| UI components, domain  |
| - Compose Nav host     |       | models, networking     |
+-----------^------------+       +-----------^------------+
            |                                    |
            |                                    |
     +------+------+                      +------+------+
     |     home    |                      |  watchlist |
     | Feature:    |                      | Feature:   |
     | Home, charts|                      | Watchlists |
     +------^------+                      +------+-----+
            |                                    |
            +---------------------+--------------+
                                  |
                            +-----v------+ 
                            |   core     |
                            | Room DB,   |
                            | utils, DI  |
                            +------------+
```

See docs/Architecture.md for details.

## Key Technologies

- Kotlin, Jetpack Compose, Compose Navigation
- Koin for dependency injection
- Room (SQLite) for local persistence
- Coroutines and Flow for async + reactive state

## License

Add your license information here.

---

## 1. Architecture

[Source: docs/Architecture.md]

# Architecture

This document explains the architecture of ETFlux and how the modules interact.

## Overview

ETFlux is organized into multiple Gradle modules with clear responsibilities:

- app: Application entry point, DI bootstrap (Koin), Compose Navigation host, and overall wiring.
- core: Core services such as Room database, entities, DAOs, utilities, and core DI module.
- home: Feature module implementing the Home screens, networking for home data, ViewModels, and state.
- watchlist: Feature module implementing Watchlist screens, repository, ViewModel, and state.
- shared: Shared UI elements (Compose components), common networking interfaces, shared domain models, and DI wiring.

The app follows a layered approach within features:
- data: networking/DAO access
- domain: domain models and repositories
- presentation: Jetpack Compose UI + ViewModels + UI state classes

## Dependency Graph

- app depends on: core, home, watchlist, shared
- home depends on: core, shared
- watchlist depends on: core, shared
- shared may depend on: core (for some utilities) where needed
- core is independent (no feature depends cycles)

Koin DI modules wire dependencies at runtime. See DIAndConfiguration.md for details.

## Data Flow

- UI (Compose) interacts with ViewModels (presentation layer)
- ViewModels call Repositories (domain layer)
- Repositories orchestrate data from Network (shared/home) and Room (core) via DAOs
- Data returned as Kotlin Flows or suspend functions

## Threading and State

- Coroutines for background tasks
- StateFlows and immutable UI state classes (e.g., HomeScreenDataState, TickerDataState, WatchListDataState)
- Compose observes state via collectAsState

## Error Handling

- Repository returns Results/Resource wrappers (see shared/domain/Resources.kt)
- UI reacts to Loading/Success/Error

## Navigation

Navigation is centralized in app module:

- EtFluxApplication initializes Koin
- Navigation.kt defines type-safe routes using Kotlin serialization for complex arguments (e.g., TickerInfoRoute with JSON-encoded Data).
- Feature screens (HomeScreen, GainerScreen, etc.) are registered as composable destinations.

See Navigation.md for examples and route classes.

## Persistence

- Room database defined in core/db/AppDataBase with entities:
    - ImageDataEntity, GainersEntity, LosersEntity, TickerInfoEntity, MonthlyStockEntity, WatchlistEntity, BookmarkEntity
- DAOs provide queries for each entity
- Version = 5; schemas are exported under core/schemas for migration tracking

See DataAndPersistence.md for more details.

---

## 2. Modules

[Source: docs/Modules.md]

# Modules

This document lists each Gradle module and its responsibilities.

## app
- EtFluxApplication bootstraps Koin (see app/src/main/java/org/soumen/etflux/EtFluxApplication.kt)
- DI initialization in di/InitKoin.kt aggregates modules: mainModule (app), HomeModule (home), sharedModule (shared), coreModule (core), watchlistModule (watchlist)
- Navigation host in presentation/presentation/Navigation.kt defines routes and destinations.

## core
- Room database (core/src/main/java/org/soumen/core/db/AppDataBase.kt)
- Entities in core/db/entities and DAOs in core/db/dao
- Core DI module (core/src/main/java/org/soumen/core/di/Module.kt)
- Utilities (e.g., status bar helpers)

## home
- Networking: data/networking/api/HomeScreenApiService.kt and response models
- Domain models under domain/dataModels
- Repository interface/implementation under domain/repository
- UI: presentation/screens (HomeScreen, GainerScreen, etc.), state classes, and HomeViewModel
- DI module (home/src/main/java/org/soumen/home/di/Module.kt)

## watchlist
- Domain models under domain/datamodules
- Repository (watchlist/domain/WatchlistRepository.kt)
- UI: WatchlistScreen, WatchListItemsScreen, states, and WatchlistViewModel
- DI module (watchlist/src/main/java/org/soumen/watchlist/di/Module.kt)

## shared
- Shared UI components: presentation/bottomBar (Bottom bar items and components)
- Shared networking services (e.g., ImageApiService, TickerInfoApiService)
- Shared domain Resource wrapper (shared/src/main/java/org/soumen/shared/domain/Resources.kt)
- DI module (shared/src/main/java/org/soumen/shared/di/Module.kt)

---

## 3. Build and Run

[Source: docs/BuildAndRun.md]

### Environment/Secrets (local setup)
Add the following entries to your local.properties (do not commit this file):

```
apikey=Q1888W3LTE7H3P5N
baseApi=www.alphavantage.co/query
baseImageApi=api.api-ninjas.com/v1/logo
imageApiKey=8NGwfRX4yrde9RLammaw5w==dGmi306WIkg7raA2
clearbitApiKey=pk_cW4oyjgyR0Sj0WIxpBfKfA
```

Note: local.properties is machine-specific and should not be committed. Ensure gradle sync picks up these properties via your module Gradle files or BuildConfig usage.

# Build and Run

## Prerequisites
- Android Studio Iguana or newer
- JDK 17
- Android SDK 24+
- Kotlin 1.9+ (managed by Gradle)

## Clone and Open
1. git clone <this-repo>
2. Open the project in Android Studio
3. Let Gradle sync finish

## Run
- Select the `app` configuration and click Run.
- Minimum supported device API is 24.

## Build from CLI
- ./gradlew assembleDebug
- ./gradlew :app:installDebug

## Tests
- ./gradlew testDebugUnitTest
- ./gradlew connectedDebugAndroidTest

## Build Variants/Flavors
If flavors are defined (e.g., development/release), select them in Build Variants. The project includes generated buildConfig for development; adjust as needed.

## Environment/Secrets
- API keys or endpoints should be configured in `core` or `shared` configs (e.g., ApiConfigs). Avoid committing secrets.
- Use local.properties or Gradle properties for local overrides.

---

## 4. Navigation

[Source: docs/Navigation.md]

# Navigation

Navigation is implemented using Jetpack Compose Navigation in the app module.

## NavHost
- The `Navigation` composable in app/src/main/java/org/soumen/etflux/presentation/presentation/Navigation.kt hosts all routes.
- It sets up bottom bar destinations and feature screens.

## Routes
- TickerInfoRoute: uses Kotlin serialization to pass a `Data` object as JSON.
- IndividualWatchlistScreenRoute: passes a `watchlistID: Long` as an argument.

Example route definitions (see actual code for details):

```kotlin
@Serializable
class TickerInfoRoute(val tickerJson: String)

class IndividualWatchlistScreenRoute(val watchlistID: Long)
```

## Transitions
- Uses fade and slide animations (tween) for enter/exit transitions.

## Passing Arguments
- Complex objects are serialized to JSON with kotlinx.serialization Json.
- Primitive args (Long, String) are part of the type-safe route.

## ViewModels
- Screen-level ViewModels are provided by Koin and passed or retrieved in composables.

## Tips
- Keep arguments small; prefer IDs and load details in destination when possible.
- Ensure types are serializable if passing as JSON.

---

## 5. Data and Persistence

[Source: docs/DataAndPersistence.md]

# Data and Persistence

This project uses Room for local data persistence and Retrofit/HTTP clients for remote data (in feature/shared modules).

## Room
- Database: core/src/main/java/org/soumen/core/db/AppDataBase.kt
- Version: 5
- Entities:
    - ImageDataEntity
    - GainersEntity
    - LosersEntity
    - TickerInfoEntity
    - MonthlyStockEntity
    - WatchlistEntity
    - BookmarkEntity
- DAOs:
    - ImageEntityDao, GainersLosersEntityDao, TickerEntityDao, MonthlyStockEntityDao, WatchlistEntityDao, BookmarkEntityDao
- Singleton access: AppDataBase.getDatabase(context) is provided for non-DI scenarios; the app uses DI (Koin) to provide the database/DAOs via `coreModule`.

## Schemas and Migrations
- Room schemas are exported under core/schemas/org.soumen.core.db.AppDataBase/ for historical versions.
- When changing entities, bump version and add a proper migration.

## Repositories
- Home and Watchlist modules include repositories that mediate between DAOs and network services.
- Repositories return Resource/Result wrappers (see shared/domain/Resources.kt) to represent loading, success, and error states.

## Best Practices
- Keep DAO methods small and specific.
- Use Flow for observing tables that change.
- For large objects, store only necessary fields.

---

## 6. DI and Configuration

[Source: docs/DIAndConfiguration.md]

# DI and Configuration

Koin is used for dependency injection and module wiring.

## Startup
- EtFluxApplication.onCreate calls `initKoin { androidContext(appContext) }`.
- `initKoin` (app/di/InitKoin.kt) calls `startKoin { modules(...) }` and aggregates:
    - mainModule (app)
    - HomeModule (home)
    - sharedModule (shared)
    - coreModule (core)
    - watchlistModule (watchlist)

## Example Koin Module

```kotlin
val watchlistModule = module {
    single { WatchlistRepository(get(), get()) }
    viewModel { WatchlistViewModel(watchlistRepository = get()) }
}
```

- Repositories receive dependencies via `get()` (e.g., DAOs, network services provided by core/shared modules).
- ViewModels are declared with `viewModel { ... }` and can be retrieved in Compose via koin or passed down.

## Adding a New Dependency
1. Decide the owning module (feature/core/shared).
2. Create a Koin module provider there.
3. Expose required classes via `single`, `factory`, or `viewModel`.
4. Add the module to `initKoin` if it is a new module.

## Configuration/BuildConfig
- Use Gradle properties and BuildConfig fields as needed per module.
- Avoid hardcoding secrets; prefer local.properties or environment variables.

---

## 7. Code Style and Conventions

[Source: docs/CodeStyleAndConventions.md]

# Code Style and Conventions

## Kotlin
- Use Kotlin official coding conventions.
- Prefer immutable data classes for UI state.
- Use explicit visibility modifiers where helpful.

## Coroutines
- Use viewModelScope in ViewModels.
- Expose StateFlow for UI states.

## Compose
- Keep composables small and stateless where possible; hoist state to ViewModel.
- Follow Material 3 theming via app/ui/theme.
- Preview composables with @Preview for fast iteration.

## Modules
- Place feature-specific code in feature modules; avoid cross-feature imports not via shared/core.
- Keep public APIs of modules minimal.

## Testing
- Unit tests under src/test; instrumented tests under src/androidTest.

## Naming
- Suffix DAO with Dao, Entity with Entity, ViewModels with ViewModel, etc.

## Documentation
- Update docs/ when adding modules or changing architecture.

---

## 8. Contributing

[Source: docs/Contributing.md]

# Contributing

Thanks for considering contributing to ETFlux!

## How to Contribute
1. Fork the repo and create a feature branch.
2. Follow the existing module boundaries and code conventions.
3. Add/update tests where applicable.
4. Update documentation under docs/ when making architectural changes.
5. Open a pull request with a clear description.

## Commit Messages
- Use concise, descriptive messages. Prefix with module if useful (e.g., "watchlist: fix delete flow").

## Code Review
- Keep PRs focused and under ~300 lines when possible.
- Provide context and screenshots/gifs for UI changes.

## Issue Reporting
- Include steps to reproduce, expected vs actual, logs if available, and device/environment details.

---

## 9. Troubleshooting

[Source: docs/Troubleshooting.md]

# Troubleshooting

## Gradle Sync Failures
- Invalidate caches/restart Android Studio.
- Ensure JDK 17 is configured.
- Check gradle.properties and wrapper versions.

## Room Migration Errors
- Ensure schema version in AppDataBase matches exported schema changes.
- Provide proper Migration objects if altering tables.

## Koin Not Starting
- Verify EtFluxApplication is registered in AndroidManifest.xml.
- Ensure initKoin is called and all modules are included.

## Network Failures
- Confirm API keys/endpoints and internet permission in AndroidManifest.

## Compose Preview Issues
- Use @Preview and provide mock ViewModel/state.
- Update Android Studio to latest Canary/Stable version if needed.

---

## 10. FAQ

[Source: docs/FAQ.md]

# FAQ

## What is ETFlux?
A modular Android app demonstrating market data browsing, watchlists, and charts using Kotlin and Compose.

## Which DI framework?
Koin.

## Which minimum Android version?
API 24.

## How is data stored?
Locally with Room (SQLite). Remote data via networking in feature/shared modules.

## Where do I start reading code?
- app module: EtFluxApplication and Navigation.kt
- home and watchlist modules for feature logic
- core for database and utilities
- shared for common components and services

## How to add a new feature module?
- Create a new Gradle module.
- Add your DI module and include it in initKoin.
- Expose your navigation destinations and integrate with NavHost.
