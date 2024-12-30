package top.rayc.expensetracker.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import top.rayc.expensetracker.ui.screen.AnalyticsScreen
import top.rayc.expensetracker.ui.screen.HomeScreen

const val HOME_ROUTE = "home_route"
fun NavController.navigateToHome(navOptions: NavOptions) = navigate(HOME_ROUTE, navOptions)
fun NavGraphBuilder.homeScreen() {
    composable(
        route = HOME_ROUTE,
        deepLinks = listOf(),
        arguments = listOf(),
    ) {
        HomeScreen()
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
