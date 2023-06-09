package com.example.githubuser.model

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DarkModePreferences private constructor(private val dataStore: DataStore<Preferences>) {
    private val themeSettingKey = booleanPreferencesKey("theme_setting")

    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[themeSettingKey] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkMode: Boolean) {
        dataStore.edit { preferences ->
            preferences[themeSettingKey] = isDarkMode
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: DarkModePreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): DarkModePreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = DarkModePreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}
