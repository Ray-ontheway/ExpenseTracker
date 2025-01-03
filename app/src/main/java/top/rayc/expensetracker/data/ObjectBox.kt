package top.rayc.expensetracker.data

import android.content.Context
import io.objectbox.BoxStore
import top.rayc.expensetracker.entity.MyObjectBox

object ObjectBox {
    lateinit var store: BoxStore
        private set

    fun init(ctx: Context) {
        store = MyObjectBox.builder()
            .androidContext(ctx)
            .build()
    }
}