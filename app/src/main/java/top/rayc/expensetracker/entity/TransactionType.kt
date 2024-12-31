package top.rayc.expensetracker.entity

enum class TransactionType(val value: Int, val description: String="") {
    INCOME(1, "INCOME"),
    EXPENSE(2, "EXPENSE")
}