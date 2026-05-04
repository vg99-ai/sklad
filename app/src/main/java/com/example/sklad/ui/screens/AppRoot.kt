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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.sklad.data.local.ProductEntity
import com.example.sklad.data.local.SaleEntity
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
                        onSale = { product, quantity ->
                            val location = locations.firstOrNull() ?: return@SalesScreen
                            val amount = quantity * product.retailPrice
                            vm.sale(
                                SaleEntity(
                                    productId = product.id,
                                    locationId = location.id,
                                    quantity = quantity,
                                    salePrice = product.retailPrice,
                                    date = System.currentTimeMillis(),
                                    customer = "",
                                    comment = "Продажа из экрана продажи",
                                    amount = amount,
                                    profit = amount - (quantity * product.purchasePrice)
                                )
                            )
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
private fun SalesScreen(products: List<ProductEntity>, onSale: (ProductEntity, Double) -> Unit) {
    var query by remember { mutableStateOf("") }
    var selectedProduct by remember { mutableStateOf<ProductEntity?>(null) }
    var quantityText by remember { mutableStateOf("1") }

    val filtered = remember(products, query) {
        if (query.isBlank()) products
        else products.filter {
            it.name.contains(query, ignoreCase = true) ||
                it.article.contains(query, ignoreCase = true) ||
                it.barcode.contains(query, ignoreCase = true)
        }
    }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Экран продажи", style = MaterialTheme.typography.headlineSmall)
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Поиск товара") },
            modifier = Modifier.fillMaxWidth()
        )

        Text("Список найденных товаров", fontWeight = FontWeight.SemiBold)
        LazyColumn(verticalArrangement = Arrangement.spacedBy(6.dp)) {
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
                            Text("Цена: ${product.retailPrice} RUB")
                        }
                        Button(onClick = {
                            selectedProduct = product
                            quantityText = "1"
                        }) { Text("Продать") }
                    }
                }
            }
        }
    }

    if (selectedProduct != null) {
        AlertDialog(
            onDismissRequest = { selectedProduct = null },
            title = { Text("Количество") },
            text = {
                OutlinedTextField(
                    value = quantityText,
                    onValueChange = { quantityText = it },
                    label = { Text("Введите количество") }
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    val quantity = quantityText.toDoubleOrNull() ?: 0.0
                    val product = selectedProduct
                    if (product != null && quantity > 0) onSale(product, quantity)
                    selectedProduct = null
                }) { Text("Продать") }
            },
            dismissButton = {
                TextButton(onClick = { selectedProduct = null }) { Text("Отмена") }
            }
        )
    }
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
