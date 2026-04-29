package com.example.sklad.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.sklad.data.local.*
import com.example.sklad.data.repository.WarehouseRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(private val repo: WarehouseRepository) : ViewModel() {
    val products = repo.productsFlow().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val locations = repo.locationsFlow().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val stocks = repo.stocksFlow().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val logs = repo.logsFlow().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun seed() = viewModelScope.launch { repo.seed() }
    fun addProduct(p: ProductEntity) = viewModelScope.launch { repo.addProduct(p) }
    fun incoming(item: IncomingEntity) = viewModelScope.launch { runCatching { repo.incomingOp(item) } }
    fun move(item: MovementEntity) = viewModelScope.launch { runCatching { repo.movementOp(item) } }
    fun sale(item: SaleEntity) = viewModelScope.launch { runCatching { repo.saleOp(item) } }
}

class MainVmFactory(private val repo: WarehouseRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = MainViewModel(repo) as T
}
