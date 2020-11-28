package edu.uoc.pac3.data

import android.content.Context
import android.preference.PreferenceManager.getDefaultSharedPreferences
import kotlin.coroutines.CoroutineContext

/**
 * Created by alex on 06/09/2020.
 */

class SessionManager(context: Context) {
    private val sharedPref = context.getSharedPreferences("myAppPrefs", Context.MODE_PRIVATE)

    private val ACCESS_TOKEN = "accessToken"
    private val REFRESH_TOKEN = "refreshToken"

    fun isUserAvailable(): Boolean {
        return sharedPref.getString(ACCESS_TOKEN, null) != null
    }

    fun getAccessToken(): String? {
        return sharedPref.getString(ACCESS_TOKEN, null)
    }

    fun saveAccessToken(accessToken: String) {
        with (sharedPref.edit()) {
            putString(ACCESS_TOKEN, accessToken)
            commit()
        }
    }

    fun clearAccessToken() {
        with(sharedPref.edit()) {
            remove(ACCESS_TOKEN)
            commit()
        }
    }

    fun getRefreshToken(): String? {
        return sharedPref.getString(REFRESH_TOKEN, null)
    }

    fun saveRefreshToken(refreshToken: String) {
        with(sharedPref.edit()) {
            putString(REFRESH_TOKEN, refreshToken)
            commit();
        }
    }

    fun clearRefreshToken() {
        with(sharedPref.edit()) {
            remove(REFRESH_TOKEN)
            commit()
        }
    }

}