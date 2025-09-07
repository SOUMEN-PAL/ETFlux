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
