package top.rayc.expensetracker.entity

import io.objectbox.converter.PropertyConverter

enum class TransactionType(val id: Int, val description: String="") {
    EXPENSE(0, "EXPENSE"),
    INCOME(1, "INCOME"),
}

fun TransactionType.isExpense(): Boolean {
    return this == TransactionType.EXPENSE
}
fun TransactionType.isIncome(): Boolean {
    return this == TransactionType.INCOME
}
