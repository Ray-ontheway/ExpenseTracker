package top.rayc.expensetracker.ui.screen

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import top.rayc.expensetracker.EApplication
import top.rayc.expensetracker.entity.TRANSACTION_DEFAULT
import top.rayc.expensetracker.navigation.navigateToTransactionEditor
import top.rayc.expensetracker.ui.component.TransactionSummary
import top.rayc.expensetracker.viewmodel.TransactionViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
internal fun HomeRoute() {
//    HomeScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    transactionViewModel: TransactionViewModel = viewModel()
) {
    val dateRangePickerState = rememberDatePickerState()
    val showDateDialog = remember { mutableStateOf(false) }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        TransactionSummary()
        FloatingActionButton(
            onClick = {
                transactionViewModel.setEditTransaction(TRANSACTION_DEFAULT)
                navController.navigateToTransactionEditor()
                Toast.makeText(EApplication.instance, "新增", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)
        ) {
            Text("新增")
        }

        if (showDateDialog.value) {
            DatePickerDialog(
                onDismissRequest = {},
                confirmButton = {
                    TextButton(
                        onClick = {
                        }
                    ) {
                        Text("Confirm")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showDateDialog.value = false
                    }) {
                        Text("Dismiss")
                    }
                }
            ) {
                DatePicker(
                    state = dateRangePickerState,
                    title = {
                        Text("Select a date range")
                    },
                    showModeToggle = false,
                    modifier = Modifier.fillMaxWidth()
                        .height(500.dp)
                        .padding(16.dp)
                )
            }
        }
    }
}