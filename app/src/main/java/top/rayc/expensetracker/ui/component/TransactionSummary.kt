package top.rayc.expensetracker.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import top.rayc.expensetracker.design.theme.Blue40
import top.rayc.expensetracker.design.theme.Green80
import top.rayc.expensetracker.design.theme.GreenGray60
import top.rayc.expensetracker.design.theme.Red40

@Composable
fun TransactionSummary() {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        TransactionSummaryItem(
            modifier = Modifier
                .background(Red40, shape = RoundedCornerShape(10.dp)),
            value = "支出: ${50f}"
        )
        Spacer(modifier = Modifier.width(20.dp))
        TransactionSummaryItem(
            modifier = Modifier
                .background(Green80, shape = RoundedCornerShape(10.dp)),
            value = "支出: ${50f}"
        )
        Spacer(modifier = Modifier.width(20.dp))
        TransactionSummaryItem(
            modifier = Modifier
                .background(Color.DarkGray, shape = RoundedCornerShape(10.dp)),
            value = "支出: ${50f}"
        )
    }
}

@Composable
fun TransactionSummaryItem(
     modifier: Modifier = Modifier,
     value: String = ""
) {
    Box(
        modifier = modifier.padding(30.dp, 20.dp)
    ) {
        Text(text = value, fontSize = 24.sp)
    }
}