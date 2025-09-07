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
