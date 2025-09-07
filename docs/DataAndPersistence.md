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
