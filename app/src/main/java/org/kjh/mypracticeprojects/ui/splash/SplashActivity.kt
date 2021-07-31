package org.kjh.mypracticeprojects.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import com.orhanobut.logger.Logger
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.kjh.mypracticeprojects.LOGIN
import org.kjh.mypracticeprojects.LOGIN_STATE
import org.kjh.mypracticeprojects.MyApplication
import org.kjh.mypracticeprojects.R
import org.kjh.mypracticeprojects.ui.login.LoginActivity
import org.kjh.mypracticeprojects.ui.main.MainActivity


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        lifecycleScope.launch {
            val targetActivity = when (MyApplication.prefs.getPref(LOGIN_STATE, 0)) {
                LOGIN -> MainActivity::class.java
                else -> LoginActivity::class.java
            }

            delay(1000)
            startActivity(Intent(this@SplashActivity, targetActivity))
        }
    }
}