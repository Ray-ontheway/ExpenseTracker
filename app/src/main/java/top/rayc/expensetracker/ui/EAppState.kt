package top.rayc.expensetracker.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.util.trace
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import kotlinx.coroutines.CoroutineScope
import top.rayc.expensetracker.navigation.ANALYTICS_ROUTE
import top.rayc.expensetracker.navigation.HOME_ROUTE
import top.rayc.expensetracker.navigation.TopLevelDestination

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
):EAppState {
    return remember(
        navController,
        coroutineScope,
    ) {
        EAppState(
            navController = navController,
            coroutineScope = coroutineScope,
        )
    }
}

@Stable
class EAppState(
    val navController: NavHostController,
    coroutineScope: CoroutineScope,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when(currentDestination?.route) {
            HOME_ROUTE -> TopLevelDestination.HOME
            ANALYTICS_ROUTE -> TopLevelDestination.ANALYTICS
            else -> null
        }

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries

    fun navigationToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        trace("Navigation: ${topLevelDestination.name}") {
            val topLevelNavOptions = navOptions {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
            when (topLevelDestination) {
                TopLevelDestination.HOME -> navController.navigate(HOME_ROUTE, topLevelNavOptions)
                TopLevelDestination.ANALYTICS -> navController.navigate(ANALYTICS_ROUTE, topLevelNavOptions)
            }
        }
    }

}