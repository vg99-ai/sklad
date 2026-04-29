package com.example.sklad.data.repository

import androidx.room.withTransaction
import com.example.sklad.data.local.*
import kotlinx.coroutines.flow.Flow

class WarehouseRepository(private val db: AppDatabase) {
    private val products = db.productDao(); private val locations = db.locationDao(); private val stocks = db.stockDao()
    private val incoming = db.incomingDao(); private val movement = db.movementDao(); private val sales = db.saleDao(); private val writeOff = db.writeOffDao(); private val logs = db.operationLogDao()

    fun productsFlow(): Flow<List<ProductEntity>> = products.getAll()
    fun locationsFlow(): Flow<List<LocationEntity>> = locations.getAll()
    fun stocksFlow(): Flow<List<StockEntity>> = stocks.getAll()
    fun logsFlow(): Flow<List<OperationLogEntity>> = logs.getAll()
    fun searchProducts(q: String) = products.search("%$q%")

    suspend fun addProduct(p: ProductEntity) = products.insert(p)
    suspend fun updateProduct(p: ProductEntity) = products.update(p)
    suspend fun deleteProduct(id: Long) = products.delete(id)

    suspend fun incomingOp(item: IncomingEntity) = db.withTransaction {
        require(item.quantity > 0)
        incoming.insert(item); changeStock(item.productId, item.locationId, item.quantity)
        logs.insert(OperationLogEntity(date = item.date, operationType = "INCOMING", productId = item.productId, quantity = item.quantity, fromLocationId = null, toLocationId = item.locationId, sum = item.purchasePrice * item.quantity, comment = item.comment))
    }

    suspend fun movementOp(item: MovementEntity) = db.withTransaction {
        require(item.quantity > 0); require(item.fromLocationId != item.toLocationId)
        ensureEnough(item.productId, item.fromLocationId, item.quantity)
        movement.insert(item); changeStock(item.productId, item.fromLocationId, -item.quantity); changeStock(item.productId, item.toLocationId, item.quantity)
        logs.insert(OperationLogEntity(date = item.date, operationType = "MOVEMENT", productId = item.productId, quantity = item.quantity, fromLocationId = item.fromLocationId, toLocationId = item.toLocationId, sum = null, comment = item.comment))
    }

    suspend fun saleOp(item: SaleEntity) = db.withTransaction {
        require(item.quantity > 0); ensureEnough(item.productId, item.locationId, item.quantity)
        sales.insert(item); changeStock(item.productId, item.locationId, -item.quantity)
        logs.insert(OperationLogEntity(date = item.date, operationType = "SALE", productId = item.productId, quantity = item.quantity, fromLocationId = item.locationId, toLocationId = null, sum = item.amount, comment = item.comment))
    }

    suspend fun writeOffOp(item: WriteOffEntity) = db.withTransaction {
        require(item.quantity > 0); ensureEnough(item.productId, item.locationId, item.quantity)
        writeOff.insert(item); changeStock(item.productId, item.locationId, -item.quantity)
        logs.insert(OperationLogEntity(date = item.date, operationType = "WRITE_OFF", productId = item.productId, quantity = item.quantity, fromLocationId = item.locationId, toLocationId = null, sum = null, comment = "${item.reason}: ${item.comment}"))
    }

    private suspend fun ensureEnough(productId: Long, locationId: Long, qty: Double) {
        val current = stocks.find(productId, locationId)?.quantity ?: 0.0
        require(current >= qty) { "Недостаточно товара на остатке" }
    }

    private suspend fun changeStock(productId: Long, locationId: Long, delta: Double) {
        val current = stocks.find(productId, locationId)
        if (current == null) stocks.insert(StockEntity(productId = productId, locationId = locationId, quantity = delta.coerceAtLeast(0.0)))
        else stocks.update(current.copy(quantity = (current.quantity + delta).coerceAtLeast(0.0)))
    }

    suspend fun seed() = db.withTransaction {
        if (products.byId(1) != null) return@withTransaction
        val wh = locations.insert(LocationEntity(name = "Основной склад", type = "Склад", addressOrComment = "Центральный"))
        val s1 = locations.insert(LocationEntity(name = "Магазин 1", type = "Магазин", addressOrComment = ""))
        locations.insert(LocationEntity(name = "Магазин 2", type = "Магазин", addressOrComment = "")); locations.insert(LocationEntity(name = "Магазин 3", type = "Магазин", addressOrComment = ""))
        val p1 = products.insert(ProductEntity(name="Молоко",article="MLK-001",barcode="460000000001",category="Молочка",unit="литры",purchasePrice=45.0,retailPrice=70.0,minStock=10.0,comment=""))
        val p2 = products.insert(ProductEntity(name="Хлеб",article="BRD-001",barcode="460000000002",category="Выпечка",unit="штуки",purchasePrice=20.0,retailPrice=35.0,minStock=15.0,comment=""))
        stocks.insert(StockEntity(productId=p1, locationId=wh, quantity=60.0)); stocks.insert(StockEntity(productId=p2, locationId=wh, quantity=80.0)); stocks.insert(StockEntity(productId=p2, locationId=s1, quantity=10.0))
    }
}
