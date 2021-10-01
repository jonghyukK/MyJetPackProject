package org.kjh.mypracticeprojects.util

import android.content.Context
import android.content.SharedPreferences
import org.kjh.mypracticeprojects.PREF_KEY_LOGIN_STATE
import org.kjh.mypracticeprojects.ui.login.LoginState

/**
 * MyPracticeProjects
 * Class: PreferencesManager
 * Created by mac on 2021/07/22.
 *
 * Description:
 */

class PreferencesManager(ctx: Context) {
    companion object {
        const val EXCEPTION_SAVE_INTO_PREF = "This type can't be saved into Preference"
        const val EXCEPTION_GET_PREF_VALUE = "Unknown Type or Empty Key"
    }

    private val prefs: SharedPreferences =
        ctx.getSharedPreferences("MY_PREF", Context.MODE_PRIVATE)

    fun <U> setPref(key: String, value: U) {
        prefs.edit().apply {
            when (value) {
                is String   -> putString (key, value)
                is Boolean  -> putBoolean(key, value)
                is Int      -> putInt    (key, value)
                else -> throw IllegalArgumentException(EXCEPTION_SAVE_INTO_PREF)
            }
        }.run {
            apply()
        }
    }

    fun <T> getPref(key: String, default: T): T {
        val res = when (default) {
            is String   -> prefs.getString (key, default)
            is Boolean  -> prefs.getBoolean(key, default)
            is Int      -> prefs.getInt    (key, default)
            else -> throw IllegalArgumentException(EXCEPTION_GET_PREF_VALUE)
        }

        return res as T
    }

    fun isLogin() = getPref(PREF_KEY_LOGIN_STATE, 0) == LoginState.LOGIN.value
}