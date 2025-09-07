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
