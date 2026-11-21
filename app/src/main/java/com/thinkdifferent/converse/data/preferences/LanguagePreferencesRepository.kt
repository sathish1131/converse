package com.thinkdifferent.converse.data.preferences

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.thinkdifferent.converse.domain.enum.Language
import com.thinkdifferent.converse.ui.state.LanguageState
import java.io.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

private const val LANGUAGE_PREFERENCES_NAME = "language_settings"
private const val TAG = "LanguagePreferences"
private val SOURCE_KEY = stringPreferencesKey("source")
private val TARGET_KEY = stringPreferencesKey("target")

private val Context.languageDataStore: DataStore<Preferences> by preferencesDataStore(
    name = LANGUAGE_PREFERENCES_NAME,
    produceMigrations = { context ->
        listOf(SharedPreferencesMigration(context, LANGUAGE_PREFERENCES_NAME))
    }
)

class LanguagePreferencesRepository(context: Context) {
    private val dataStore = context.applicationContext.languageDataStore

    val state: Flow<LanguageState> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Failed to read saved language settings", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            LanguageState(
                source = preferences[SOURCE_KEY]?.toLanguage() ?: Language.HINDI,
                target = preferences[TARGET_KEY]?.toLanguage() ?: Language.ENGLISH
            )
        }

    suspend fun update(source: Language, target: Language) {
        dataStore.edit { preferences ->
            preferences[SOURCE_KEY] = source.name
            preferences[TARGET_KEY] = target.name
        }
    }

    private fun String.toLanguage(): Language =
        Language.entries.firstOrNull { it.name == this } ?: Language.HINDI
}
