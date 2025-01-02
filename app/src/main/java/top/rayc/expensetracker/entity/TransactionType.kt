package top.rayc.expensetracker.entity

enum class TransactionType(val value: Int, val description: String="") {
    EXPENSE(0, "EXPENSE"),
    INCOME(1, "INCOME"),
}

fun TransactionType.isExpense(): Boolean {
    return this == TransactionType.EXPENSE
}
fun TransactionType.isIncome(): Boolean {
    return this == TransactionType.INCOME
}