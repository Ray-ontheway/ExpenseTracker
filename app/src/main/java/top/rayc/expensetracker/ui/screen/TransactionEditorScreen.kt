package top.rayc.expensetracker.ui.screen

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import top.rayc.expensetracker.EApplication
import top.rayc.expensetracker.entity.TransactionCategory
import top.rayc.expensetracker.entity.TransactionType
import top.rayc.expensetracker.entity.isIncome
import top.rayc.expensetracker.ui.EApp
import top.rayc.expensetracker.viewmodel.TransactionViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

const val TAG = "TransactionEditorScreen"

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TransactionEditorScreen(
    navController: NavController,
    transactionViewModel: TransactionViewModel = viewModel()
) {
    val transactionRecord by transactionViewModel.editTransaction.collectAsState()

    val topLevelCategories by transactionViewModel.topLevelCategories.collectAsState()
    val incomeCategories = topLevelCategories.filter { it.type == TransactionType.INCOME }
    val expenseCategories = topLevelCategories.filter { it.type == TransactionType.EXPENSE }

    var amount by remember { mutableStateOf(if(transactionRecord.amount>0f) transactionRecord.amount.toString() else "") }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            CategorySelector(
                selectedCategoryTypeVal = transactionRecord.type.id,
                incomeCategories = incomeCategories,
                expenseCategories = expenseCategories,
                selectedCategory = transactionRecord.category,
                onTypeChange = {
                    transactionViewModel.setEditTransaction(
                        transactionRecord.copy(type = it)
                    )
                },
                onSelected = {
                    transactionViewModel.setEditTransaction(
                        transactionRecord.copy(category = it))
                },
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = amount,
                    onValueChange = {
                        if (it.isBlank()) {
                            amount = ""
                        } else if (it.toFloatOrNull() != null) {
                            amount = it
                        }
                    },
                    label = { Text("Amount", color = Color.Gray) },
                    placeholder = { Text("0", color = Color.Gray) },

                )
                Spacer(modifier = Modifier.width(16.dp))
                DatePickerModal(
                    modifier = Modifier.weight(1f),
                    onDateSelected = {
                        transactionViewModel.setEditTransaction(
                            transactionRecord.copy(createAt = LocalDate.ofEpochDay(it!!))
                        )
                    },
                    onDismiss = {},
                    curDate = transactionRecord.createAt.toEpochDay()
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = transactionRecord.comment,
                onValueChange = {
                    transactionViewModel.setEditTransaction(
                        transactionRecord.copy(comment = it)
                    )
                },
                label = { Text("Comment", color = Color.Gray) },
                maxLines = 5,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("是否冲动消费: ", fontSize = 18.sp, color = Color.Gray)
                Spacer(modifier = Modifier.width(16.dp))
                Switch(
                    checked = transactionRecord.impl,
                    onCheckedChange = {
                        transactionViewModel.setEditTransaction(
                            transactionRecord.copy(impl = it)
                        )
                    },
                    modifier = Modifier.height(16.dp).width(32.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    val amountVal = amount.toFloatOrNull()
                    if (amountVal == null) {
                        Toast.makeText(EApplication.instance, "Amount is invalid", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    transactionViewModel.setEditTransaction(
                        transactionRecord.copy(
                            amount = amount.toFloat(),
                        )
                    )
                    transactionViewModel.saveEditTransaction()
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Save") }
        }
    }
}

@Composable
fun CategorySelector(
    selectedCategoryTypeVal: Int,
    incomeCategories: List<TransactionCategory>,
    expenseCategories: List<TransactionCategory>,
    selectedCategory: TransactionCategory?,
    onSelected: (TransactionCategory?) -> Unit = {},
    onTypeChange: (TransactionType) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        TabRow(
            selectedTabIndex = selectedCategoryTypeVal,
            modifier = Modifier.fillMaxWidth()
        ) {
            Tab(
                selected = selectedCategoryTypeVal == TransactionType.EXPENSE.id,
                onClick = { onTypeChange(TransactionType.EXPENSE) },
                text = { Text(TransactionType.EXPENSE.description) },
            )
            Tab(
                selected = selectedCategoryTypeVal == TransactionType.INCOME.id,
                onClick = { onTypeChange(TransactionType.INCOME) },
                text = { Text(TransactionType.INCOME.description) },
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        when (selectedCategoryTypeVal) {
            TransactionType.INCOME.id -> {
                CategorySelector(
                    categories = incomeCategories,
                    selectedCategory = selectedCategory,
                    onSelected = { cat -> onSelected(cat) }
                )
            }
            TransactionType.EXPENSE.id -> {
                CategorySelector(
                    categories = expenseCategories,
                    selectedCategory = selectedCategory,
                    onSelected = { cat -> onSelected(cat) }
                )
            }
        }
    }
}

@Composable
fun CategorySelector(
    modifier: Modifier = Modifier,
    selectedCategory: TransactionCategory? = null,
    categories: List<TransactionCategory>,
    onSelected: (TransactionCategory?) -> Unit = {},
) {
    LazyColumn (
        modifier = modifier.padding(0.dp, 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(categories.size) { index ->
            CategorySubSelector(
                category = categories[index],
                selectedCategory = selectedCategory,
                onSelected = onSelected
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategorySubSelector(
    modifier: Modifier = Modifier,
    selectedCategory: TransactionCategory? = null,
    category: TransactionCategory,
    onSelected: (TransactionCategory?) -> Unit = {},
) {
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(category.name ?: "", color = Color.Gray)
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            category.children.forEach { child ->
                CategorySelectorItem(
                    selected = selectedCategory?.id == child.id,
                    category = child,
                    onSelected = onSelected
                )
            }
        }
    }
}

@Composable
fun CategorySelectorItem(
    selected: Boolean = false,
    category: TransactionCategory,
    onSelected: (TransactionCategory?) -> Unit = {},
) {
    Box (
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(
                if (selected)
                    if (category.type.isIncome()) Color(0xFF32cd32)
                    else Color(0xFFFF6347)
                else Color.White,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(onClick = { onSelected(if (selected) null else category) })
            .padding(16.dp, 8.dp)
    ) { Text(category.name ?: "") }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit,
    curDate: Long? = null,
    modifier: Modifier = Modifier,
) {
    val datePickerState = rememberDatePickerState()
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<Long?>(curDate) }
    OutlinedTextField(
        value = selectedDate?.let { convertMillisToDate(it) } ?: "",
        onValueChange = { selectedDate = it.toLongOrNull() },
        label = { Text("DOB", color = Color.Gray) },
        placeholder = { Text("MM/DD/YYYY") },
        trailingIcon = {
            Icon(Icons.Default.DateRange, contentDescription = "Select Date")
        },
        modifier = modifier.pointerInput(selectedDate) {
            awaitEachGesture {
                // Modifier.clickable doesn't work for text fields, so we use Modifier.pointerInput
                // in the Initial pass to observe events before the text field consumes them
                // in the Main pass.
                awaitFirstDown(pass = PointerEventPass.Initial)
                val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                if (upEvent != null) {
                    showDatePicker = true
                }
            }
        }
    )
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = {
                    selectedDate = datePickerState.selectedDateMillis?.let {
                        it / 86400000
                    }
                    showDatePicker = false
                    onDateSelected(selectedDate)
                }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDatePicker = false
                    onDismiss()
                }) {
                    Text("Dismiss")
                }
            }) {
            DatePicker(state = datePickerState)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun convertMillisToDate(millis: Long): String {
    return LocalDate.ofEpochDay(millis)
        .format(DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.getDefault()))
}
