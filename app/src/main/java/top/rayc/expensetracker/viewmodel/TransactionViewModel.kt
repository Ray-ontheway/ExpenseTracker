package top.rayc.expensetracker.viewmodel

import android.os.Build
import android.util.Log
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
import top.rayc.expensetracker.entity.TransactionCategory
import top.rayc.expensetracker.entity.TransactionRecord
import top.rayc.expensetracker.entity.defaultCategories
import top.rayc.expensetracker.vo.TRANSACTION_DEFAULT
import top.rayc.expensetracker.vo.TransactionRecordVO

const val TAG = "TransactionViewModel"

class TransactionViewModel: ViewModel() {

    private val _transactionRecords: MutableStateFlow<List<TransactionRecord>> =
        MutableStateFlow(emptyList())
    private fun loadTransactionRecords() {
        _transactionRecords.value = emptyList()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private val _transactionRecordVOs = _transactionRecords.map { records ->
        records.map { record ->
            TransactionRecordVO(
                id = record.id,
                amount = record.amount,
                category = defaultCategories.find { it.id == record.categoryId },
                type = record.type,
                comment = record.comment,
                impl = record.impl,
                createAt = record.createAt
            )
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    @RequiresApi(Build.VERSION_CODES.O)
    val transactionRecordVOs: StateFlow<List<TransactionRecordVO>> = _transactionRecordVOs

    @RequiresApi(Build.VERSION_CODES.O)
    private val _editTransaction = MutableStateFlow<TransactionRecordVO>(TRANSACTION_DEFAULT)
    @RequiresApi(Build.VERSION_CODES.O)
    val editTransaction: StateFlow<TransactionRecordVO> = _editTransaction
    @RequiresApi(Build.VERSION_CODES.O)
    fun setEditTransaction(transactionRecord: TransactionRecordVO) {
        _editTransaction.value = transactionRecord
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun saveEditTransaction() {
        Log.e(TAG, "saveEditTransaction: ${editTransaction.value}")
        Toast.makeText(EApplication.instance, "保存", Toast.LENGTH_SHORT).show()

    }

    private val _transactionCategories: MutableStateFlow<List<TransactionCategory>> =
        MutableStateFlow(emptyList())
    private fun loadTransactionCategories () {
        _transactionCategories.value = defaultCategories
    }
    val topLevelCategories: StateFlow<List<TransactionCategory>> = _transactionCategories
        .map { categories ->
            categories.filter { it.pid == 0L }
                .map { category ->
                    category.copy(
                        children = categories.filter { it.pid == category.id }
                    )
                }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        loadTransactionCategories()
        loadTransactionRecords()
    }

}