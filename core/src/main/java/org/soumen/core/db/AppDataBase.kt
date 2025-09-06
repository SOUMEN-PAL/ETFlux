package org.soumen.core.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import kotlinx.coroutines.InternalCoroutinesApi
import org.soumen.core.db.dao.GainersLosersEntityDao
import org.soumen.core.db.dao.ImageEntityDao
import org.soumen.core.db.dao.MonthlyStockEntityDao
import org.soumen.core.db.dao.TickerEntityDao
import org.soumen.core.db.entities.GainersEntity
import org.soumen.core.db.entities.ImageDataEntity
import org.soumen.core.db.entities.LosersEntity
import org.soumen.core.db.entities.MonthlyStockEntity
import org.soumen.core.db.entities.TickerInfoEntity

/**
 * The Room database for this app.
 *
 * This class defines the database configuration and serves as the main access point
 * for the underlying connection to your app's persisted, relational data.
 *
 * It uses the [ImageDataEntity] as its only entity.
 * The database version is currently 1.
 */
@Database(
    entities = [ImageDataEntity::class , GainersEntity::class , LosersEntity::class , TickerInfoEntity::class , MonthlyStockEntity::class],
    version = 4
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun imageDao() : ImageEntityDao

    abstract fun gainersLosersDao() : GainersLosersEntityDao

    abstract fun tickerInfoDao() : TickerEntityDao

    abstract fun monthlyStockEntityDao() : MonthlyStockEntityDao


    companion object{
        @Volatile
        private var INSTANCE: AppDataBase? = null

        /**
         * Returns an instance of the [AppDataBase].
         *
         * This method ensures that only one instance of the database is created
         * using a synchronized block to prevent race conditions. If an instance
         * already exists, it is returned. Otherwise, a new instance is created
         * using [Room.databaseBuilder].
         *Use it only when you are not using DI and want to create Database as a singleton
         * call it in Application
         *
         * The database is named "appDB".
         *
         * @param context The application context.
         * @return The singleton instance of [AppDataBase].
         *         The return type is nullable for safety, although in practice
         *         it will always return a non-null instance after the first call.
         *         The `!!` operator is used internally as we ensure `INSTANCE` is not null.
         */
        @OptIn(InternalCoroutinesApi::class)
        fun getDatabase(context: Context): AppDataBase? {
            if(INSTANCE == null){
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(context,
                        AppDataBase::class.java ,
                        "appDB")
                        .build()
                }
            }
            return  INSTANCE!!
        }

    }

}