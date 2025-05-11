package com.example.jobfinder.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.jobfinder.data.remote.dto.response.LoginUserResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.io.IOException

// Define the DataStore at file level
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_session")

object UserSessionManager {
    private lateinit var applicationContext: Context

    // Initialize in your Application class
    fun initialize(context: Context) {
        if (!::applicationContext.isInitialized) {
            applicationContext = context.applicationContext
        }
    }

    private object PreferencesKeys {
        val USER_ID = intPreferencesKey("user_id")
        val USER_NAME = stringPreferencesKey("user_name")
        val EMAIL = stringPreferencesKey("email")
        val AUTH_TOKEN = stringPreferencesKey("auth_token")
        val ROLE = stringPreferencesKey("role")
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    }

    // Save user data after login
    suspend fun setUserData(loginUserResponse: LoginUserResponse) {
        applicationContext.dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_ID] = loginUserResponse.id
            preferences[PreferencesKeys.USER_NAME] = loginUserResponse.fullName
            preferences[PreferencesKeys.EMAIL] = loginUserResponse.email
            preferences[PreferencesKeys.AUTH_TOKEN] = loginUserResponse.token
            preferences[PreferencesKeys.ROLE] = loginUserResponse.role
            preferences[PreferencesKeys.IS_LOGGED_IN] = true
        }
    }

    // Clear on logout
    suspend fun clearUserData() {
        applicationContext.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    // Get user data as flows
    val userIdFlow: Flow<Int>
        get() = applicationContext.dataStore.data
            .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
            .map { it[PreferencesKeys.USER_ID] ?: 0 }

    val userNameFlow: Flow<String>
        get() = applicationContext.dataStore.data
            .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
            .map { it[PreferencesKeys.USER_NAME] ?: "" }

    val emailFlow: Flow<String>
        get() = applicationContext.dataStore.data
            .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
            .map { it[PreferencesKeys.EMAIL] ?: "" }

    val authTokenFlow: Flow<String>
        get() = applicationContext.dataStore.data
            .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
            .map { it[PreferencesKeys.AUTH_TOKEN] ?: "" }

    val isLoggedInFlow: Flow<Boolean>
        get() = applicationContext.dataStore.data
            .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
            .map { it[PreferencesKeys.IS_LOGGED_IN] ?: false }

    // For synchronous access (use carefully)
    fun getUserId(): Int {
        var userId = 0
        runBlocking {
            userIdFlow.first().let { userId = it }
        }
        return userId
    }

    fun getAuthToken(): String {
        var token = ""
        runBlocking {
            authTokenFlow.first().let { token = it }
        }
        return token
    }

    fun getUserName(): String {
        var userName = ""
        runBlocking {
            userNameFlow.first().let { userName = it }
        }
        return userName
    }

    fun getEmail(): String {
        var email = ""
        runBlocking {
            emailFlow.first().let { email = it }
        }
        return email
    }

    fun isLoggedIn(): Boolean {
        var isLoggedIn = false
        runBlocking {
            isLoggedInFlow.first().let { isLoggedIn = it }
        }
        return isLoggedIn
    }
}