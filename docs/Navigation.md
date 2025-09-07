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
