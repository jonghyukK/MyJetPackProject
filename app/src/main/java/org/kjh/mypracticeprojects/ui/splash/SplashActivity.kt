package org.kjh.mypracticeprojects.ui.splash

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.kjh.mypracticeprojects.*
import org.kjh.mypracticeprojects.databinding.ActivitySplashBinding
import org.kjh.mypracticeprojects.ui.base.BaseActivity
import org.kjh.mypracticeprojects.ui.main.MainActivity


class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    companion object {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }

    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()) {
                goToNextActivity()
        }

        onCheckPermission()
    }

    private fun goToNextActivity() {
        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
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