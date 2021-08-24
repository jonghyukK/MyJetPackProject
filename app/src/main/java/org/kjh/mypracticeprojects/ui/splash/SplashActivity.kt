package org.kjh.mypracticeprojects.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.kjh.mypracticeprojects.*
import org.kjh.mypracticeprojects.ui.login.LoginActivity
import org.kjh.mypracticeprojects.ui.main.MainActivity


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        lifecycleScope.launch {
            val targetActivity = when (
                MyApplication.prefs.getPref(PREF_KEY_LOGIN_STATE, 0)) {
                LoginState.LOGIN.value -> MainActivity::class.java
                else                   -> LoginActivity::class.java
            }
            delay(1000)
            startActivity(Intent(this@SplashActivity, targetActivity))
        }
    }
}