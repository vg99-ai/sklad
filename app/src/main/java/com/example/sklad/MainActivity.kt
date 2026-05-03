package com.example.sklad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.sklad.data.local.AppDatabase
import com.example.sklad.data.repository.WarehouseRepository
import com.example.sklad.ui.screens.AppRoot
import com.example.sklad.ui.viewmodel.MainVmFactory
import com.example.sklad.ui.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    private val vm: MainViewModel by viewModels { MainVmFactory(WarehouseRepository(AppDatabase.get(this))) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm.seed()
        setContent { AppRoot(vm) }
    }
}
