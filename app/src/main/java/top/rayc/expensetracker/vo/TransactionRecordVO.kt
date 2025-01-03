package top.rayc.expensetracker.vo

import android.os.Build
import androidx.annotation.RequiresApi
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Id
import top.rayc.expensetracker.entity.TransactionCategory
import top.rayc.expensetracker.entity.TransactionType
import top.rayc.expensetracker.entity.TransactionTypeConverter
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
data class TransactionRecordVO (
    @Id var id: Long = 0,
    var amount: Float = 0f,
    var category: TransactionCategory? = null,
    @Convert(converter = TransactionTypeConverter::class, dbType = Int::class)
    var type: TransactionType = TransactionType.EXPENSE,
    var comment: String = "",
    var impl: Boolean = false,
    var createAt: LocalDate = LocalDate.now(),
)

@RequiresApi(Build.VERSION_CODES.O)
val TRANSACTION_DEFAULT = TransactionRecordVO(amount = 0f)
