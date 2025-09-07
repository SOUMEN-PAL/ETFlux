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
