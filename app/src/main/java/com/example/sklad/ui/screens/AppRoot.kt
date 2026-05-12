package com.example.sklad.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.sklad.data.local.ProductEntity
import com.example.sklad.data.local.SaleEntity
import com.example.sklad.data.local.StockEntity
import com.example.sklad.ui.viewmodel.MainViewModel

private enum class AppScreen(val title: String) {
    Dashboard("Сводка"),
    Products("Товары"),
    Sales("Продажа"),
    Scanner("Сканер")
}

@Composable
fun AppRoot(vm: MainViewModel) {
    val products by vm.products.collectAsState()
    val locations by vm.locations.collectAsState()
    val stocks by vm.stocks.collectAsState()
    val logs by vm.logs.collectAsState()

    var currentScreen by remember { mutableStateOf(AppScreen.Dashboard) }

    MaterialTheme {
        Scaffold(
            bottomBar = {
                NavigationBar {
                    AppScreen.entries.forEach { screen ->
                        NavigationBarItem(
                            selected = currentScreen == screen,
                            onClick = { currentScreen = screen },
                            icon = { Text("•") },
                            label = { Text(screen.title) }
                        )
                    }
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                when (currentScreen) {
                    AppScreen.Dashboard -> DashboardScreen(
                        productCount = products.size,
                        totalStock = stocks.sumOf { it.quantity },
                        operationsCount = logs.size
                    )

                    AppScreen.Products -> ProductsScreen(
                        products = products,
                        onAdd = vm::addProduct
                    )

                    AppScreen.Sales -> SalesScreen(
                        products = products,
                        stocks = stocks,
                        onCompleteSale = { saleRows ->
                            saleRows.forEach { row ->
                                val stockLocation = stocks
                                    .filter { it.productId == row.product.id }
                                    .sortedByDescending { it.quantity }
                                    .firstOrNull()
                                    ?: return@forEach

                                val amount = row.quantity * row.product.retailPrice
                                vm.sale(
                                    SaleEntity(
                                        productId = row.product.id,
                                        locationId = stockLocation.locationId,
                                        quantity = row.quantity,
                                        salePrice = row.product.retailPrice,
                                        date = System.currentTimeMillis(),
                                        customer = "",
                                        comment = "Продажа из кассы",
                                        amount = amount,
                                        profit = amount - (row.quantity * row.product.purchasePrice)
                                    )
                                )
                            }
                        }
                    )

                    AppScreen.Scanner -> ScannerScreen(products = products)
                }
            }
        }
    }
}

@Composable
private fun DashboardScreen(productCount: Int, totalStock: Double, operationsCount: Int) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Главный экран", style = MaterialTheme.typography.headlineSmall)
        MetricCard("Количество товаров", productCount.toString())
        MetricCard("Общий остаток", totalStock.toString())
        MetricCard("Количество операций", operationsCount.toString())
    }
}

@Composable
private fun MetricCard(label: String, value: String) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(label, style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(6.dp))
            Text(value, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun ProductsScreen(products: List<ProductEntity>, onAdd: (ProductEntity) -> Unit) {
    var name by remember { mutableStateOf("") }
    var article by remember { mutableStateOf("") }
    var barcode by remember { mutableStateOf("") }
    var purchaseEur by remember { mutableStateOf("") }
    var saleRub by remember { mutableStateOf("") }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Экран товаров", style = MaterialTheme.typography.headlineSmall)
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Название") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = article, onValueChange = { article = it }, label = { Text("Артикул") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = barcode, onValueChange = { barcode = it }, label = { Text("Штрихкод") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = purchaseEur, onValueChange = { purchaseEur = it }, label = { Text("Закупка EUR") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = saleRub, onValueChange = { saleRub = it }, label = { Text("Продажа RUB") }, modifier = Modifier.fillMaxWidth())

        Button(
            onClick = {
                if (name.isBlank()) return@Button
                val purchase = purchaseEur.toDoubleOrNull() ?: 0.0
                val sale = saleRub.toDoubleOrNull() ?: 0.0
                onAdd(
                    ProductEntity(
                        name = name,
                        article = article,
                        barcode = barcode,
                        category = "",
                        unit = "шт",
                        purchasePrice = purchase,
                        retailPrice = sale,
                        minStock = 0.0,
                        comment = ""
                    )
                )
                name = ""
                article = ""
                barcode = ""
                purchaseEur = ""
                saleRub = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Добавить") }

        Text("Список товаров", fontWeight = FontWeight.SemiBold)
        LazyColumn(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            items(products) { product ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(product.name, fontWeight = FontWeight.Bold)
                        Text("Артикул: ${product.article}")
                        Text("Штрихкод: ${product.barcode}")
                        Text("Закупка EUR: ${product.purchasePrice}")
                        Text("Продажа RUB: ${product.retailPrice}")
                    }
                }
            }
        }
    }
}

@Composable
private fun SalesScreen(
    products: List<ProductEntity>,
    stocks: List<StockEntity>,
    onCompleteSale: (List<CartRow>) -> Unit
) {
    var query by remember { mutableStateOf("") }
    val cart = remember { mutableStateListOf<CartRow>() }
    var message by remember { mutableStateOf<String?>(null) }

    val filtered = remember(products, query) {
        if (query.isBlank()) products
        else products.filter {
            it.name.contains(query, ignoreCase = true) ||
                it.article.contains(query, ignoreCase = true) ||
                it.barcode.contains(query, ignoreCase = true)
        }
    }

    val totalQty = cart.sumOf { it.quantity }
    val totalAmount = cart.sumOf { it.quantity * it.product.retailPrice }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Экран продажи (Касса)", style = MaterialTheme.typography.headlineSmall)
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Поиск товара") },
            modifier = Modifier.fillMaxWidth()
        )

        Text("Найденные товары", fontWeight = FontWeight.SemiBold)
        LazyColumn(modifier = Modifier.height(180.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            items(filtered) { product ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(product.name, fontWeight = FontWeight.Bold)
                            Text("Артикул: ${product.article}")
                            Text("Штрихкод: ${product.barcode}")
                        }
                        Button(onClick = { addToCart(cart, product) }) { Text("В корзину") }
                    }
                }
            }
        }

        Text("Корзина", fontWeight = FontWeight.SemiBold)
        LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            itemsIndexed(cart, key = { _, row -> row.product.id }) { index, row ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(row.product.name, fontWeight = FontWeight.Bold)
                        Text("Кол-во: ${row.quantity}")
                        Text("Цена за шт: ${row.product.retailPrice} RUB")
                        Text("Сумма: ${row.quantity * row.product.retailPrice} RUB")
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(onClick = { cart[index] = row.copy(quantity = row.quantity + 1.0) }) { Text("+") }
                            Button(onClick = {
                                val newQty = row.quantity - 1.0
                                if (newQty <= 0.0) cart.removeAt(index) else cart[index] = row.copy(quantity = newQty)
                            }) { Text("-") }
                            Button(onClick = { cart.removeAt(index) }) { Text("Удалить") }
                        }
                    }
                }
            }
        }

        Text("Итого товаров: $totalQty")
        Text("Итого сумма: $totalAmount RUB")

        Button(
            onClick = {
                val errors = validateCartAgainstStocks(cart, stocks)
                if (errors.isNotEmpty()) {
                    message = errors.first()
                    return@Button
                }
                onCompleteSale(cart.toList())
                cart.clear()
                message = "Продажа завершена"
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Завершить продажу") }

        if (message != null) {
            Text(message!!)
        }
    }
}

data class CartRow(val product: ProductEntity, val quantity: Double)

private fun addToCart(cart: SnapshotStateList<CartRow>, product: ProductEntity) {
    val index = cart.indexOfFirst { it.product.id == product.id }
    if (index >= 0) {
        val row = cart[index]
        cart[index] = row.copy(quantity = row.quantity + 1.0)
    } else {
        cart.add(CartRow(product = product, quantity = 1.0))
    }
}

private fun validateCartAgainstStocks(cart: List<CartRow>, stocks: List<StockEntity>): List<String> {
    val errors = mutableListOf<String>()
    cart.forEach { row ->
        val available = stocks.filter { it.productId == row.product.id }.sumOf { it.quantity }
        if (available <= 0.0) {
            errors.add("${row.product.name}: товара нет в остатках")
        } else if (available < row.quantity) {
            errors.add("${row.product.name}: остаток $available меньше количества ${row.quantity}")
        }
    }
    return errors
}

@Composable
private fun ScannerScreen(products: List<ProductEntity>) {
    var barcodeInput by remember { mutableStateOf("") }
    var result by remember { mutableStateOf<ProductEntity?>(null) }
    var searchDone by remember { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Экран сканера", style = MaterialTheme.typography.headlineSmall)
        OutlinedTextField(
            value = barcodeInput,
            onValueChange = { barcodeInput = it },
            label = { Text("Введите штрихкод") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = {
            result = products.find { it.barcode == barcodeInput.trim() }
            searchDone = true
        }) { Text("Найти товар") }

        if (searchDone) {
            if (result != null) {
                Text("Товар найден:")
                Text("Название: ${result?.name}")
                Text("Артикул: ${result?.article}")
                Text("Продажа RUB: ${result?.retailPrice}")
            } else {
                Text("Товар не найден")
            }
        }
    }
}
