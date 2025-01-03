package top.rayc.expensetracker.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_preferences")

private object Contants {
    val IS_SETUP_COMPLETED = stringPreferencesKey("is_setup_completed")
}
class AppPreferenceDataStore(private val context: Context) {

    val isSetupCompleted: Flow<Boolean?> = context.dataStore.data
        .catch { exception ->
            if (exception is Exception) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[Contants.IS_SETUP_COMPLETED]?.toBoolean()
        }

    suspend fun setIsSetupCompleted(isSetupCompleted: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[Contants.IS_SETUP_COMPLETED] = isSetupCompleted.toString()
        }
    }

}