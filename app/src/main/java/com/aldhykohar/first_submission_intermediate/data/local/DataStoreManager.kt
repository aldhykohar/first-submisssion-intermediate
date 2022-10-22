package com.aldhykohar.first_submission_intermediate.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.aldhykohar.first_submission_intermediate.data.model.login.LoginResult
import com.aldhykohar.first_submission_intermediate.utils.UtilConstants.DATA_STORE_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


/**
 * Created by aldhykohar on 10/22/2022.
 */

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)

class DataStoreManager(context: Context) {
    private var appContext = context.applicationContext

    val getIsLogin: Flow<Boolean> =
        appContext.dataStore.data.map { preferences -> preferences[KEY_LOGIN] ?: false }

    suspend fun setIsLogin(value: Boolean) {
        appContext.dataStore.edit { preferences -> preferences[KEY_LOGIN] = value }
    }

    val getDataUser: Flow<LoginResult> =
        appContext.dataStore.data.map { preferences ->
            LoginResult(
                name = preferences[KEY_USER_NAME] ?: "",
                userId = preferences[KEY_USER_ID] ?: "",
                token = preferences[KEY_USER_TOKEN] ?: ""
            )
        }

    suspend fun setDataUser(data: LoginResult) {
        appContext.dataStore.edit { preferences ->
            preferences[KEY_USER_ID] = data.userId ?: ""
            preferences[KEY_USER_NAME] = data.name ?: ""
            preferences[KEY_USER_TOKEN] = data.token ?: ""
        }
    }

    suspend fun clearDataStore() {
        appContext.dataStore.edit { it.clear() }
    }

    companion object {
        private val KEY_LOGIN = booleanPreferencesKey("is_login")
        private val KEY_USER_ID = stringPreferencesKey("id_user")
        private val KEY_USER_NAME = stringPreferencesKey("name")
        private val KEY_USER_TOKEN = stringPreferencesKey("token")
    }
}