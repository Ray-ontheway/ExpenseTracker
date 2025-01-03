package top.rayc.expensetracker.ui

import android.os.Build
import android.view.Window
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.safeGestures
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import top.rayc.expensetracker.design.component.EBackground
import top.rayc.expensetracker.design.component.ENavigationSuiteScaffold
import top.rayc.expensetracker.navigation.ANALYTICS_ROUTE
import top.rayc.expensetracker.navigation.ENavHost
import top.rayc.expensetracker.navigation.HOME_ROUTE
import top.rayc.expensetracker.navigation.TopLevelDestination

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EApp(
    appState: EAppState,
    modifier: Modifier = Modifier,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
) {
    EBackground(
        modifier = modifier,
    ) {
        val snackbarHostState = remember { SnackbarHostState() }
        EApp(
            appState = appState,
            snackbarHostState = snackbarHostState,
            windowAdaptiveInfo = windowAdaptiveInfo
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun EApp(
    appState: EAppState,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
) {
    val currentDestination = appState.currentDestination

    ENavigationSuiteScaffold(
        navigationSuiteItems = {
            appState.topLevelDestinations.forEach { destination ->
                val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
                item(
                    selected = selected,
                    onClick = { appState.navigationToTopLevelDestination(destination) },
                    icon = { Icon(imageVector = destination.unselectedIcon, contentDescription = null) },
                    selectedIcon = { Icon(imageVector = destination.selectedIcon, contentDescription = null) },
                    label = { Text(stringResource(destination.iconTextId)) },
                    modifier = Modifier.testTag("ENavItem")
                )
            }
        },
        windowAdaptiveInfo = windowAdaptiveInfo,
        showNavigationBar = currentDestination.isTopLevelDestination(),
    ) {
        Scaffold(
            modifier = modifier.semantics { testTagsAsResourceId = true },
            containerColor = Color.Transparent,
            contentColor =  MaterialTheme.colorScheme.onBackground,
            snackbarHost = { SnackbarHost(snackbarHostState) },
        ) { innerPadding ->
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(innerPadding)
                    .consumeWindowInsets(innerPadding)
                    .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal))
            ) {
                Box(
                    modifier = Modifier.consumeWindowInsets(
                        WindowInsets(0, 0, 0, 0)
                    )
                ) {
                    ENavHost(
                        appState = appState,
                        modifier = modifier
                    )
                }
            }
        }
    }
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination): Boolean {
    return this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false
}

private fun NavDestination?.isTopLevelDestination(): Boolean {
    return this?.route?.equals(HOME_ROUTE) ?: false || this?.route?.equals(ANALYTICS_ROUTE) ?: false
}