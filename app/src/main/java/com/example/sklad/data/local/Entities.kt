package com.example.sklad.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val article: String,
    val barcode: String,
    val category: String,
    val unit: String,
    val purchasePrice: Double,
    val retailPrice: Double,
    val minStock: Double,
    val comment: String
)

@Entity(tableName = "locations")
data class LocationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val type: String,
    val addressOrComment: String
)

@Entity(
    tableName = "stocks",
    foreignKeys = [
        ForeignKey(entity = ProductEntity::class, parentColumns = ["id"], childColumns = ["productId"]),
        ForeignKey(entity = LocationEntity::class, parentColumns = ["id"], childColumns = ["locationId"])
    ],
    indices = [Index(value = ["productId", "locationId"], unique = true)]
)
data class StockEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val productId: Long,
    val locationId: Long,
    val quantity: Double
)

@Entity(tableName = "incoming")
data class IncomingEntity(@PrimaryKey(autoGenerate = true) val id: Long = 0, val productId: Long, val quantity: Double, val purchasePrice: Double, val locationId: Long, val date: Long, val supplier: String, val docNumber: String, val comment: String)
@Entity(tableName = "movements")
data class MovementEntity(@PrimaryKey(autoGenerate = true) val id: Long = 0, val productId: Long, val fromLocationId: Long, val toLocationId: Long, val quantity: Double, val date: Long, val comment: String)
@Entity(tableName = "sales")
data class SaleEntity(@PrimaryKey(autoGenerate = true) val id: Long = 0, val productId: Long, val locationId: Long, val quantity: Double, val salePrice: Double, val date: Long, val customer: String, val comment: String, val amount: Double, val profit: Double)
@Entity(tableName = "write_offs")
data class WriteOffEntity(@PrimaryKey(autoGenerate = true) val id: Long = 0, val productId: Long, val locationId: Long, val quantity: Double, val reason: String, val date: Long, val comment: String)
@Entity(tableName = "operation_logs")
data class OperationLogEntity(@PrimaryKey(autoGenerate = true) val id: Long = 0, val date: Long, val operationType: String, val productId: Long, val quantity: Double, val fromLocationId: Long?, val toLocationId: Long?, val sum: Double?, val comment: String)
