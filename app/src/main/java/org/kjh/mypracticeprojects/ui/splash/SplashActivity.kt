package org.kjh.mypracticeprojects.ui.splash

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.kjh.mypracticeprojects.*
import org.kjh.mypracticeprojects.databinding.ActivitySplashBinding
import org.kjh.mypracticeprojects.ui.base.BaseActivity
import org.kjh.mypracticeprojects.ui.main.MainActivity


class SplashActivity
    : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    companion object {
        const val PERM_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
        const val PERM_STORAGE  = Manifest.permission.READ_EXTERNAL_STORAGE
    }

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()) {
        goToNextActivity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onCheckPermission()
    }

    private fun onCheckPermission() {
        val isHaveLocationPerm = havePermission(PERM_LOCATION)
        val isHaveStoragePerm  = havePermission(PERM_STORAGE)

        if (!isHaveLocationPerm || !isHaveStoragePerm) {
            permissionLauncher.launch(arrayOf(PERM_LOCATION, PERM_STORAGE))
        } else {
            lifecycleScope.launch {
                delay(2000)
                goToNextActivity()
            }
        }
    }

    private fun goToNextActivity() {
        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
    }

    private fun havePermission(permName: String) =
        ContextCompat.checkSelfPermission(this, permName) == PackageManager.PERMISSION_GRANTED
}