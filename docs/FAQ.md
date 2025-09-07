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
