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

### Local properties (example)
Define the following in local.properties for local development:

```
apikey=Q1888W3LTE7H3P5N
baseApi=www.alphavantage.co/query
baseImageApi=api.api-ninjas.com/v1/logo
imageApiKey=8NGwfRX4yrde9RLammaw5w==dGmi306WIkg7raA2
clearbitApiKey=pk_cW4oyjgyR0Sj0WIxpBfKfA
```

Then, read them in Gradle or at runtime via BuildConfig or a configuration provider.
