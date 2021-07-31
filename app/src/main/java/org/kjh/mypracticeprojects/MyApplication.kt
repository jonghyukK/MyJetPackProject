package org.kjh.mypracticeprojects

import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import dagger.hilt.android.HiltAndroidApp
import org.kjh.mypracticeprojects.util.PreferencesManager

/**
 * MyPracticeProjects
 * Class: MyApplication
 * Created by mac on 2021/07/21.
 *
 * Description:
 */
@HiltAndroidApp
class MyApplication: Application() {

    companion object {
        lateinit var prefs: PreferencesManager
    }

    override fun onCreate() {
        prefs = PreferencesManager(applicationContext)
        super.onCreate()

        Logger.addLogAdapter(AndroidLogAdapter())
    }
}