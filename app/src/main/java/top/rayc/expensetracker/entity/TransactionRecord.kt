package top.rayc.expensetracker.entity

import android.os.Build
import androidx.annotation.RequiresApi
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Entity
data class TransactionRecord (
    @Id var id: Long = 0,
    var amount: Float = 0f,
    var categoryId: Long? = null,
    @Convert(converter = TransactionTypeConverter::class, dbType = Int::class)
    var type: TransactionType = TransactionType.EXPENSE,
    var comment: String = "",
    var impl: Boolean = false,
    @Convert(converter = LocalDateConverter::class, dbType = Long::class)
    var createAt: LocalDate = LocalDate.now(),
)
