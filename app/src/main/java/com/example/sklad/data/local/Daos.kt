package com.example.sklad.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM products ORDER BY name") fun getAll(): Flow<List<ProductEntity>>
    @Query("SELECT * FROM products WHERE name LIKE :q OR article LIKE :q OR barcode LIKE :q") fun search(q: String): Flow<List<ProductEntity>>
    @Insert suspend fun insert(item: ProductEntity): Long
    @Update suspend fun update(item: ProductEntity)
    @Query("DELETE FROM products WHERE id=:id") suspend fun delete(id: Long)
    @Query("SELECT * FROM products WHERE id=:id") suspend fun byId(id: Long): ProductEntity?
}

@Dao
interface LocationDao {
    @Query("SELECT * FROM locations ORDER BY id") fun getAll(): Flow<List<LocationEntity>>
    @Insert suspend fun insert(item: LocationEntity): Long
}

@Dao
interface StockDao {
    @Query("SELECT * FROM stocks") fun getAll(): Flow<List<StockEntity>>
    @Query("SELECT * FROM stocks WHERE productId=:productId AND locationId=:locationId") suspend fun find(productId: Long, locationId: Long): StockEntity?
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insert(item: StockEntity)
    @Update suspend fun update(item: StockEntity)
}

@Dao interface IncomingDao { @Insert suspend fun insert(item: IncomingEntity) }
@Dao interface MovementDao { @Insert suspend fun insert(item: MovementEntity) }
@Dao interface SaleDao { @Insert suspend fun insert(item: SaleEntity) }
@Dao interface WriteOffDao { @Insert suspend fun insert(item: WriteOffEntity) }
@Dao interface OperationLogDao { @Insert suspend fun insert(item: OperationLogEntity); @Query("SELECT * FROM operation_logs ORDER BY date DESC") fun getAll(): Flow<List<OperationLogEntity>> }
