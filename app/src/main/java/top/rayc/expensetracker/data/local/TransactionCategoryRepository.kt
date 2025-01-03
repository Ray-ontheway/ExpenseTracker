package top.rayc.expensetracker.data.local

import io.objectbox.Box
import top.rayc.expensetracker.data.ObjectBox
import top.rayc.expensetracker.entity.TransactionCategory

class TransactionCategoryRepository {

    private val box: Box<TransactionCategory> = ObjectBox.store.boxFor(TransactionCategory::class.java)

    fun add(transactionCategories: List<TransactionCategory>) {
        box.put(transactionCategories)
    }

    fun add(transactionCategory: TransactionCategory) {
        box.put(transactionCategory)
    }

    fun findAll(): List<TransactionCategory> {
        return box.all
    }

    fun find(id: Long): TransactionCategory? {
        return box.get(id)
    }

    fun remove(transactionCategory: TransactionCategory) {
        box.remove(transactionCategory)
    }

    fun remove(id: Long) {
        box.remove(id)
    }

}