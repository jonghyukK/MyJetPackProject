package org.kjh.mypracticeprojects.ui.splash

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.orhanobut.logger.Logger
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.kjh.mypracticeprojects.*
import org.kjh.mypracticeprojects.ui.login.LoginActivity
import org.kjh.mypracticeprojects.ui.main.MainActivity


class SplashActivity : AppCompatActivity() {

    companion object {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }

    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()) {
                goToNextActivity()
        }

        onCheckPermission()
    }

    private fun goToNextActivity() {
        val targetActivity = when (
            MyApplication.prefs.getPref(PREF_KEY_LOGIN_STATE, 0)) {
            LoginState.LOGIN.value -> MainActivity::class.java
            else                   -> LoginActivity::class.java
        }

        startActivity(Intent(this@SplashActivity, targetActivity))
    }

    private fun onCheckPermission() {
        val isHaveLocationPerm = havePermission(permissions[0])
        val isHaveStoragePerm = havePermission(permissions[1])

        if (!isHaveLocationPerm || !isHaveStoragePerm) {
            permissionLauncher.launch(permissions)
        } else {
            lifecycleScope.launch {
                delay(1000)
                goToNextActivity()
            }
        }
    }

    private fun havePermission(permName: String) =
        ContextCompat.checkSelfPermission(this, permName) == PackageManager.PERMISSION_GRANTED
}