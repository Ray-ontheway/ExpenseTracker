package top.rayc.expensetracker.viewmodel

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import top.rayc.expensetracker.EApplication
import top.rayc.expensetracker.entity.TRANSACTION_DEFAULT
import top.rayc.expensetracker.entity.TransactionRecord

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

}