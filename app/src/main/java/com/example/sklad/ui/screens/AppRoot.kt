package com.example.sklad.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sklad.data.local.IncomingEntity
import com.example.sklad.data.local.MovementEntity
import com.example.sklad.data.local.ProductEntity
import com.example.sklad.data.local.SaleEntity
import com.example.sklad.ui.viewmodel.MainViewModel

@Composable
fun AppRoot(vm: MainViewModel) {
    var screen by remember { mutableStateOf("Главная") }
    val products by vm.products.collectAsState()
    val locations by vm.locations.collectAsState()
    val stocks by vm.stocks.collectAsState()
    val logs by vm.logs.collectAsState()

    MaterialTheme {
        Column(Modifier.fillMaxSize().padding(12.dp)) {
            if (screen == "Главная") {
                listOf("Товары", "Остатки", "Приход", "Перемещение", "Продажа", "Журнал", "Отчёты", "Сканер").forEach {
                    Button(onClick = { screen = it }, modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) { Text(it) }
                }
            } else {
                TextButton(onClick = { screen = "Главная" }) { Text("← Назад") }
                when (screen) {
                    "Товары" -> ProductsScreen(products) { vm.addProduct(it) }
                    "Остатки" -> StocksScreen(products, locations, stocks)
                    "Приход" -> IncomingScreen(products, locations) { vm.incoming(it) }
                    "Перемещение" -> MoveScreen(products, locations) { vm.move(it) }
                    "Продажа" -> SaleScreen(products, locations) { vm.sale(it) }
                    "Журнал" -> LogsScreen(logs)
                    "Отчёты" -> ReportScreen(products, stocks)
                    else -> Text("Сканер штрихкода: заглушка интерфейса. Можно подключить ML Kit позже.")
                }
            }
        }
    }
}

@Composable fun ProductsScreen(products: List<ProductEntity>, onAdd: (ProductEntity) -> Unit) { var name by remember { mutableStateOf("") }; OutlinedTextField(name,{name=it}, label={Text("Название")}); Button(onClick={ if(name.isNotBlank()) onAdd(ProductEntity(name=name,article="",barcode="",category="",unit="штуки",purchasePrice=0.0,retailPrice=0.0,minStock=0.0,comment="")); name="" }){Text("Добавить")}; LazyColumn{ items(products){ Text("• ${it.name} (${it.article})") } } }
@Composable fun StocksScreen(products: List<ProductEntity>, locations: List<com.example.sklad.data.local.LocationEntity>, stocks: List<com.example.sklad.data.local.StockEntity>) { LazyColumn { items(stocks){ s -> val p=products.find{it.id==s.productId}?.name?:"?"; val l=locations.find{it.id==s.locationId}?.name?:"?"; Text("$p / $l: ${s.quantity}") } } }
@Composable fun IncomingScreen(products: List<ProductEntity>, locations: List<com.example.sklad.data.local.LocationEntity>, onSave:(IncomingEntity)->Unit){ if(products.isEmpty()||locations.isEmpty()){Text("Нет данных");return}; Button(onClick={ onSave(IncomingEntity(productId=products.first().id,quantity=1.0,purchasePrice=1.0,locationId=locations.first().id,date=System.currentTimeMillis(),supplier="",docNumber="",comment="быстрый приход")) }){Text("Быстрый приход +1")}}
@Composable fun MoveScreen(products: List<ProductEntity>, locations: List<com.example.sklad.data.local.LocationEntity>, onSave:(MovementEntity)->Unit){ if(products.isEmpty()||locations.size<2){Text("Нужно >=2 точки");return}; Button(onClick={onSave(MovementEntity(productId=products.first().id,fromLocationId=locations.first().id,toLocationId=locations[1].id,quantity=1.0,date=System.currentTimeMillis(),comment="быстрое перемещение"))}){Text("Переместить 1 шт")}}
@Composable fun SaleScreen(products: List<ProductEntity>, locations: List<com.example.sklad.data.local.LocationEntity>, onSave:(SaleEntity)->Unit){ if(products.isEmpty()||locations.isEmpty()){Text("Нет данных");return}; val p=products.first(); Button(onClick={onSave(SaleEntity(productId=p.id,locationId=locations.first().id,quantity=1.0,salePrice=p.retailPrice,date=System.currentTimeMillis(),customer="",comment="быстрая продажа",amount=p.retailPrice,profit=p.retailPrice-p.purchasePrice))}){Text("Продать 1 шт")}}
@Composable fun LogsScreen(logs: List<com.example.sklad.data.local.OperationLogEntity>){ LazyColumn{ items(logs){ Text("${it.operationType}: товар ${it.productId}, кол-во ${it.quantity}") } } }
@Composable fun ReportScreen(products: List<ProductEntity>, stocks: List<com.example.sklad.data.local.StockEntity>){ val total=stocks.sumOf{it.quantity}; val low=products.count{p-> stocks.filter{it.productId==p.id}.sumOf{it.quantity}<=p.minStock }; Text("Всего позиций: ${products.size}\nОбщий остаток: $total\nНизкий остаток: $low") }
