package top.rayc.expensetracker.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import top.rayc.expensetracker.ui.EAppState

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ENavHost(
    appState: EAppState,
    modifier: Modifier = Modifier,
    startDestination: String = HOME_ROUTE,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        homeScreen(navController)
        analyticsScreen()
        transactionEditorScreen()
    }

}