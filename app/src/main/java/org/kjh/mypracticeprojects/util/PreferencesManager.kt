package org.kjh.mypracticeprojects.util

import android.content.Context
import android.content.SharedPreferences
import okhttp3.internal.cache2.Relay.Companion.edit
import org.kjh.mypracticeprojects.PREF_KEY_LOGIN_STATE
import java.lang.IllegalArgumentException

/**
 * MyPracticeProjects
 * Class: PreferencesManager
 * Created by mac on 2021/07/22.
 *
 * Description:
 */

class PreferencesManager(ctx: Context) {

    private val prefs: SharedPreferences =
        ctx.getSharedPreferences("MY_PREF", Context.MODE_PRIVATE)

    fun <U> setPref(key: String, value: U) {
        prefs.edit().apply {
            when (value) {
                is String   -> putString(key, value)
                is Boolean  -> putBoolean(key, value)
                is Int      -> putInt(key, value)
                else -> throw IllegalArgumentException("This type cant be saved into Prefrences")
            }
        }.run {
            apply()
        }
    }

    fun <T> getPref(key: String, default: T): T {
        val res = when (default) {
            is String   -> prefs.getString(key, default)
            is Boolean  -> prefs.getBoolean(key, default)
            is Int      -> prefs.getInt(key, default)
            else -> throw IllegalArgumentException("Data is Null")
        }

        return res as T
    }
}