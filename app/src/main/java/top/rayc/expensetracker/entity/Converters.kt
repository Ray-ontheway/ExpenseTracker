package top.rayc.expensetracker.entity

import android.os.Build
import androidx.annotation.RequiresApi
import io.objectbox.converter.PropertyConverter
import java.time.LocalDate

class TransactionTypeConverter : PropertyConverter<TransactionType?, Int?> {
    override fun convertToEntityProperty(databaseValue: Int?): TransactionType? {
        if (databaseValue == null) {
            return null
        }
        for (role in TransactionType.entries) {
            if (role.id == databaseValue) {
                return role
            }
        }
        return TransactionType.EXPENSE
    }

    override fun convertToDatabaseValue(entityProperty: TransactionType?): Int? {
        return entityProperty?.id
    }
}

class LocalDateConverter : PropertyConverter<LocalDate?, Long?> {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun convertToEntityProperty(databaseValue: Long?): LocalDate? {
        return databaseValue?.let { LocalDate.ofEpochDay(it) }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun convertToDatabaseValue(entityProperty: LocalDate?): Long? {
        return entityProperty?.toEpochDay()
    }
}