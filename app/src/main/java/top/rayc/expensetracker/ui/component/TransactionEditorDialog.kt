package top.rayc.expensetracker.ui.component

import android.app.Dialog
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import top.rayc.expensetracker.design.theme.DarkGreenGray10
import top.rayc.expensetracker.entity.TransactionRecord
import top.rayc.expensetracker.entity.TransactionType
import top.rayc.expensetracker.entity.defaultCategories
import java.time.LocalDate
import java.time.LocalDateTime


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TransactionEditorDialog(
    modifier: Modifier = Modifier,
    transactionRecord: TransactionRecord? = null,
) {
    val TAG = "TransactionEditorDialog"
    val testState = remember {
         mutableStateOf(transactionRecord
             ?: TransactionRecord(
                 0,
                 0.0f,
                 defaultCategories[2],
                 TransactionType.EXPENSE,
                 "",
                 date = LocalDate.now())
         )
    }
    Dialog(
        onDismissRequest = { },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Column(
            modifier =Modifier.background(Color.LightGray, shape = RoundedCornerShape(8.dp))
                .padding(16.dp)
        ) {
            TransactionEditor(
                transactionRecord = testState.value,
                onConfirm = {
                    Log.e(TAG, "TransactionEditorDialog: $it")
                }
            )
        }
    }
}

@Composable
fun TransactionEditor(
    modifier: Modifier = Modifier,
    transactionRecord: TransactionRecord? = null,
    onConfirm: ((TransactionRecord) -> Unit)? = null,
) {
    val transactionState = remember { mutableStateOf(transactionRecord) }
    Column {
        Row {
            OutlinedTextField(
                placeholder = { Text("金额") },
                value = transactionRecord?.amount.toString(),
                onValueChange = { transactionState.value?.apply {
                    amount = it.toFloat()
                } }
            )
        }
    }
}