package top.rayc.expensetracker.design.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItemColors
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemColors
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteItemColors
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import top.rayc.expensetracker.design.EIcons
import top.rayc.expensetracker.design.theme.ETheme

@Composable
fun RowScope.ENavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    alwaysShowLabel: Boolean = true,
    icon: @Composable () -> Unit,
    selectedIcon: @Composable () -> Unit,
    label: @Composable (() -> Unit)? = null,
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = ENavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = ENavigationDefaults.navigationContentColor(),
            selectedTextColor = ENavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = ENavigationDefaults.navigationContentColor(),
            indicatorColor = ENavigationDefaults.navigationIndicatorColor()
        )
    )
}

@Composable fun ENavigationBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    NavigationBar(
        modifier = modifier,
        contentColor = ENavigationDefaults.navigationContentColor(),
        tonalElevation = 0.dp,
        content = content
    )
}

@Composable
fun RsNavigationRailItem(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    alwaysShowLabel: Boolean = true,
    icon: @Composable () -> Unit,
    selectedIcon: @Composable () -> Unit,
    label: @Composable (() -> Unit)? = null,
) {
    NavigationRailItem(
        selected = selected,
        onClick = onClick,
        icon = icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors = NavigationRailItemDefaults.colors(
            selectedIconColor = ENavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = ENavigationDefaults.navigationContentColor(),
            selectedTextColor = ENavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = ENavigationDefaults.navigationContentColor(),
            indicatorColor = ENavigationDefaults.navigationIndicatorColor()
        )
    )
}

@Composable
fun ENavigationRail(
    modifier: Modifier = Modifier,
    header: @Composable (ColumnScope.() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    NavigationRail(
        modifier = modifier,
        containerColor = Color.Transparent,
        contentColor = ENavigationDefaults.navigationContentColor(),
        header = header,
        content = content
    )
}


@Composable
fun ENavigationSuiteScaffold(
    navigationSuiteItems: ENavigationSuiteScope.() -> Unit,
    modifier: Modifier = Modifier,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
    showNavigationBar: Boolean = true,
    content: @Composable () -> Unit,
) {
    val layoutType = NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(windowAdaptiveInfo)
    val navigationSuiteItemColors = NavigationSuiteItemColors(
        navigationBarItemColors = NavigationBarItemDefaults.colors(
            selectedIconColor = ENavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = ENavigationDefaults.navigationContentColor(),
            selectedTextColor = ENavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = ENavigationDefaults.navigationContentColor(),
            indicatorColor = ENavigationDefaults.navigationIndicatorColor()
        ),
        navigationRailItemColors = NavigationRailItemDefaults.colors(
            selectedIconColor = ENavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = ENavigationDefaults.navigationContentColor(),
            selectedTextColor = ENavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = ENavigationDefaults.navigationContentColor(),
            indicatorColor = ENavigationDefaults.navigationIndicatorColor()
        ),
        navigationDrawerItemColors = NavigationDrawerItemDefaults.colors(
            selectedIconColor = ENavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = ENavigationDefaults.navigationContentColor(),
            selectedTextColor = ENavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = ENavigationDefaults.navigationContentColor(),
        )
    )

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            if (showNavigationBar) {
                ENavigationSuiteScope(
                    navigationSuiteScope = this,
                    navigationSuiteItemColors = navigationSuiteItemColors
                ).run(navigationSuiteItems)
            }
        },
        layoutType = layoutType,
        containerColor = Color.Transparent,
        navigationSuiteColors = NavigationSuiteDefaults.colors(
            navigationBarContentColor = ENavigationDefaults.navigationContentColor(),
            navigationRailContainerColor = Color.Transparent,
        ),
        modifier = modifier,
    ) {
        content()
    }
}

class ENavigationSuiteScope internal constructor(
    private val navigationSuiteScope: NavigationSuiteScope,
    private val navigationSuiteItemColors: NavigationSuiteItemColors,
) {
    fun item(
        selected: Boolean,
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        icon: @Composable () -> Unit,
        selectedIcon: @Composable () -> Unit,
        label: @Composable (() -> Unit)? = null,
    ) = navigationSuiteScope.item(
        selected = selected,
        onClick = onClick,
        icon = { if (selected) selectedIcon() else icon() },
        modifier = modifier,
        label = label,
        colors = navigationSuiteItemColors
    )
}

object ENavigationDefaults {
    @Composable
    fun navigationContentColor() = MaterialTheme.colorScheme.onSurfaceVariant

    @Composable
    fun navigationSelectedItemColor() = MaterialTheme.colorScheme.onPrimaryContainer

    @Composable
    fun navigationIndicatorColor() = MaterialTheme.colorScheme.primaryContainer
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light theme")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark theme")
@Composable
fun NiaNavigationBarPreview() {
    val items = listOf("主页", "仪表盘")
    val icons = listOf(
        EIcons.HomeBorder,
        EIcons.AnalyticsBorder,
    )
    val selectedIcons = listOf(
        EIcons.Home,
        EIcons.Analytics,
    )

    ETheme  {
        ENavigationBar {
            items.forEachIndexed { index, item ->
                ENavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = icons[index],
                            contentDescription = item,
                        )
                    },
                    selectedIcon = {
                        Icon(
                            imageVector = selectedIcons[index],
                            contentDescription = item,
                        )
                    },
                    label = { Text(item) },
                    selected = index == 0,
                    onClick = { },
                )
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light theme")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark theme")
@Composable
fun NiaNavigationRailPreview() {
    val items = listOf("主页", "仪表盘")
    val icons = listOf(
        EIcons.HomeBorder,
        EIcons.AnalyticsBorder,
    )
    val selectedIcons = listOf(
        EIcons.Home,
        EIcons.Analytics,
    )

    ETheme  {
        ENavigationRail  {
            items.forEachIndexed { index, item ->
                RsNavigationRailItem(
                    icon = {
                        Icon(
                            imageVector = icons[index],
                            contentDescription = item,
                        )
                    },
                    selectedIcon = {
                        Icon(
                            imageVector = selectedIcons[index],
                            contentDescription = item,
                        )
                    },
                    label = { Text(item) },
                    selected = index == 0,
                    onClick = { },
                )
            }
        }
    }
}

