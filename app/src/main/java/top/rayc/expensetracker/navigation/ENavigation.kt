package top.rayc.expensetracker.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import top.rayc.expensetracker.ui.screen.AnalyticsScreen
import top.rayc.expensetracker.ui.screen.HomeScreen
import top.rayc.expensetracker.ui.screen.TransactionEditorScreen

const val HOME_ROUTE = "home_route"
fun NavController.navigateToHome(navOptions: NavOptions) = navigate(HOME_ROUTE, navOptions)
@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.homeScreen(navController: NavController) {
    composable(
        route = HOME_ROUTE,
        deepLinks = listOf(),
        arguments = listOf(),
    ) {
        HomeScreen(navController = navController)
    }
}

const val ANALYTICS_ROUTE = "analytics_route"
fun NavController.navigateToAnalytics(navOptions: NavOptions) = navigate(ANALYTICS_ROUTE, navOptions)
fun NavGraphBuilder.analyticsScreen() {
    composable(
        route = ANALYTICS_ROUTE,
        deepLinks = listOf(),
        arguments = listOf(),
    ) {
        AnalyticsScreen()
    }
}

const val TRANSACTION_EDITOR_ROUTE = "$HOME_ROUTE/transaction_editor_route"
fun NavController.navigateToTransactionEditor(
    options: NavOptions? = navOptions {}
) = navigate(TRANSACTION_EDITOR_ROUTE, options)
fun NavGraphBuilder.transactionEditorScreen() {
    composable(
        route = TRANSACTION_EDITOR_ROUTE,
        deepLinks = listOf(),
        arguments = listOf(),
    ) {
         TransactionEditorScreen()
    }
}
