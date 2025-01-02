package top.rayc.expensetracker.ui.screen

import android.os.Build
import android.util.Log
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
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import top.rayc.expensetracker.entity.TransactionCategory
import top.rayc.expensetracker.entity.TransactionType
import top.rayc.expensetracker.entity.isIncome
import top.rayc.expensetracker.viewmodel.TransactionViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Date
import java.util.Locale

const val TAG = "TransactionEditorScreen"

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TransactionEditorScreen(
    transactionViewModel: TransactionViewModel = viewModel()
) {
    val topLevelCategories by transactionViewModel.topLevelCategories.collectAsState()
    val incomeCategories = topLevelCategories.filter { it.type == TransactionType.INCOME }
    val expenseCategories = topLevelCategories.filter { it.type == TransactionType.EXPENSE }
    var selectedCategoryTypeVal by remember { mutableIntStateOf(TransactionType.EXPENSE.value) }

    var selectedCategory by remember { mutableStateOf<TransactionCategory?>(null) }
    val transactionRecord by transactionViewModel.editTransaction.collectAsState()
    var amount by remember { mutableStateOf(if(transactionRecord.amount>0f) transactionRecord.amount.toString() else "") }
    var comment by remember { mutableStateOf(transactionRecord.comment) }
    var curDate by remember { mutableStateOf(transactionRecord.date.toEpochDay()) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            CategorySelector(
                selectedCategoryTypeVal = selectedCategoryTypeVal,
                incomeCategories = incomeCategories,
                expenseCategories = expenseCategories,
                selectedCategory = selectedCategory,
                onTypeChange = { selectedCategoryTypeVal = it.value },
                onSelected = { selectedCategory = it },
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = amount,
                    onValueChange = {
                        if (it.toFloatOrNull() == null) {
                            if (it.isEmpty()) {
                                amount = ""
                            }
                        } else {
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
                        curDate = it!!
                    },
                    onDismiss = {
                    },
                    curDate = curDate
                )
            }
            OutlinedTextField(
                value = comment,
                onValueChange = {
                    comment = it
                },
                label = { Text("Comment", color = Color.Gray) },
                maxLines = 5,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    Log.e(TAG, "TransactionEditorScreen: selectedType: $selectedCategory")
                    Log.e(TAG, "TransactionEditorScreen: amount: $amount")
                    Log.e(TAG, "TransactionEditorScreen: date: ${LocalDate.ofEpochDay(curDate)}")
                    Log.e(TAG, "TransactionEditorScreen: comment: $comment")
                    transactionViewModel.editTransaction.value.copy(
                        comment = comment,
                        amount = amount.toFloat(),
                        category = selectedCategory!!,
                        date = LocalDate.ofEpochDay(curDate),
                        type = TransactionType.entries.find { it.value == selectedCategoryTypeVal }!!
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save")
            }
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
                selected = selectedCategoryTypeVal == TransactionType.EXPENSE.value,
                onClick = { onTypeChange(TransactionType.EXPENSE) },
                text = { Text(TransactionType.EXPENSE.description) },
            )
            Tab(
                selected = selectedCategoryTypeVal == TransactionType.INCOME.value,
                onClick = { onTypeChange(TransactionType.INCOME) },
                text = { Text(TransactionType.INCOME.description) },
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        when (selectedCategoryTypeVal) {
            TransactionType.INCOME.value -> {
                CategorySelector(
                    categories = incomeCategories, selectedCategory = selectedCategory, onSelected = { cat ->
                    onSelected(cat)
                })
            }
            TransactionType.EXPENSE.value -> {
                CategorySelector(categories = expenseCategories, selectedCategory = selectedCategory, onSelected = { cat ->
                    onSelected(cat)
                })
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
        Text(category.name, color = Color.Gray)
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            category.children.forEach { child ->
                CategorySelectorItem(
                    selected = selectedCategory?.id == child.id,
                    category = child,
                    onSelected = onSelected)
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
            .clickable(
                onClick = {
                    onSelected(if (selected) null else category)
                }
            )
            .padding(16.dp, 8.dp)
    ) {
        Text(category.name)
    }
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
    Log.e(TAG, "DatePickerModal: ${Date(curDate!!)}")

    OutlinedTextField(
        value = selectedDate?.let { convertMillisToDate(it) } ?: "",
        onValueChange = {
            selectedDate = it.toLongOrNull()
        },
        label = { Text("DOB", color = Color.Gray) },
        placeholder = { Text("MM/DD/YYYY") },
        trailingIcon = {
            Icon(Icons.Default.DateRange, contentDescription = "Select Date")
        },

        modifier = modifier
            .pointerInput(selectedDate) {
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
