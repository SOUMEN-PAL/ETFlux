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
