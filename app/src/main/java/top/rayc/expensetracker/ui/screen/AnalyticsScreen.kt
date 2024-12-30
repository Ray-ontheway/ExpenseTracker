package top.rayc.expensetracker.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun AnalyticsRoute(

) {
    AnalyticsScreen()
}

@Composable
internal fun AnalyticsScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        Text("Analytics Screen")
    }
}