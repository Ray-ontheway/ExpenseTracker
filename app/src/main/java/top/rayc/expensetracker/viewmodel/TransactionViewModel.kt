package top.rayc.expensetracker.viewmodel

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import top.rayc.expensetracker.EApplication
import top.rayc.expensetracker.entity.TRANSACTION_DEFAULT
import top.rayc.expensetracker.entity.TransactionCategory
import top.rayc.expensetracker.entity.TransactionRecord
import top.rayc.expensetracker.entity.defaultCategories

class TransactionViewModel: ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    private val _editTransaction = MutableStateFlow<TransactionRecord>(TRANSACTION_DEFAULT)
    @RequiresApi(Build.VERSION_CODES.O)
    val editTransaction: StateFlow<TransactionRecord> = _editTransaction
    @RequiresApi(Build.VERSION_CODES.O)
    fun setEditTransaction(transactionRecord: TransactionRecord) {
        _editTransaction.value = transactionRecord
    }
    fun saveEditTransaction() {
        Toast.makeText(EApplication.instance, "保存", Toast.LENGTH_SHORT).show()
    }


    private val _transactionCategories: MutableStateFlow<List<TransactionCategory>> =
        MutableStateFlow(emptyList())
    private fun loadTransactionCategories () {
        _transactionCategories.value = defaultCategories
    }
    val topLevelCategories: StateFlow<List<TransactionCategory>> = _transactionCategories
        .map { categories ->
            categories.filter { it.pid == 0 }
                .map { category ->
                    category.copy(
                        children = categories.filter { it.pid == category.id }
                    )
                }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        loadTransactionCategories()
    }

}