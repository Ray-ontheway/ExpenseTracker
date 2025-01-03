package top.rayc.expensetracker.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.navOptions
import kotlinx.coroutines.delay
import top.rayc.expensetracker.EApplication
import top.rayc.expensetracker.data.local.TransactionCategoryRepository
import top.rayc.expensetracker.entity.defaultCategories
import top.rayc.expensetracker.navigation.SPLASH_ROUTE
import top.rayc.expensetracker.navigation.navigateToHome

@Composable
fun SplashScreen(
    navController: NavController,
) {
    var isSetupCompleted by remember { mutableStateOf(false) }
    val categoryRepository = TransactionCategoryRepository()

    LaunchedEffect(Unit) {
        EApplication.appPreferenceDataStore.isSetupCompleted.collect {
            if (it == true) {
                Log.e(TAG, "SplashScreen: 无需再次setup")
                isSetupCompleted = true
            } else {
                categoryRepository.add(defaultCategories.map { cat -> cat.copy(id = 0) })
                Log.e(TAG, "SplashScreen: ${categoryRepository.findAll()}")
                EApplication.appPreferenceDataStore.setIsSetupCompleted(true)
            }
        }
    }

    LaunchedEffect(Unit) {
        delay(2_000)
        while (!isSetupCompleted) {
            delay(100)
        }
        navController.navigateToHome(navOptions {
            popUpTo(SPLASH_ROUTE) { inclusive = true }
        })
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text("Splash")
    }
}