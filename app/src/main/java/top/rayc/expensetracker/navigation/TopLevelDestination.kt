package top.rayc.expensetracker.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import top.rayc.expensetracker.R
import top.rayc.expensetracker.design.EIcons

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int,
    val titleTextId: Int,
) {
    HOME(
        selectedIcon = EIcons.Home,
        unselectedIcon = EIcons.HomeBorder,
        iconTextId = R.string.home,
        titleTextId = R.string.home
    ),
    ANALYTICS(
        selectedIcon = EIcons.Analytics,
        unselectedIcon = EIcons.AnalyticsBorder,
        iconTextId = R.string.analytics,
        titleTextId = R.string.analytics
    ),
}