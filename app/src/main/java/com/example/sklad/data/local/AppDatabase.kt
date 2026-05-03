package com.example.sklad.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ProductEntity::class, LocationEntity::class, StockEntity::class, IncomingEntity::class, MovementEntity::class, SaleEntity::class, WriteOffEntity::class, OperationLogEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun locationDao(): LocationDao
    abstract fun stockDao(): StockDao
    abstract fun incomingDao(): IncomingDao
    abstract fun movementDao(): MovementDao
    abstract fun saleDao(): SaleDao
    abstract fun writeOffDao(): WriteOffDao
    abstract fun operationLogDao(): OperationLogDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null
        fun get(context: Context): AppDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(context, AppDatabase::class.java, "sklad.db").build().also { INSTANCE = it }
        }
    }
}
