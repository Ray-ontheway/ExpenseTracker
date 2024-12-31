package top.rayc.expensetracker.entity

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

data class TransactionRecord(
    val id: Int,
    var amount: Float,
    val category: TransactionCategory,
    val type: TransactionType,
    val comment: String,
    val impl: Boolean = false,
    val date: LocalDate,
)

@RequiresApi(Build.VERSION_CODES.O)
val TRANSACTION_DEFAULT = TransactionRecord(
    id = 0,
    amount = 0f,
    category = defaultCategories[2],
    type = TransactionType.EXPENSE,
    comment = "",
    date = LocalDate.now()
)