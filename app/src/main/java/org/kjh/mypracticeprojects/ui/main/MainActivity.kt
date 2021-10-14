package org.kjh.mypracticeprojects.ui.main

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.messaging.FirebaseMessaging
import com.orhanobut.logger.Logger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import org.kjh.mypracticeprojects.*
import org.kjh.mypracticeprojects.databinding.ActivityMainBinding
import org.kjh.mypracticeprojects.ui.base.BaseActivity
import org.kjh.mypracticeprojects.ui.main.home.LoginSignUpBottomSheet
import org.kjh.mypracticeprojects.util.PreferencesManager

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    companion object {
        const val LOGIN_SIGNUP_BOTTOM_SHEET = "LOGIN_SIGNUP_BOTTOM_SHEET"
        const val ERROR_GET_FCM_TOKEN       = "Failed FCM registration Token"
    }

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBottomNavigationView()
        initFirebaseMessagingToken()
    }

    private fun initFirebaseMessagingToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { fcmTask ->
            // when error
            if (!fcmTask.isSuccessful) {
                Logger.e(ERROR_GET_FCM_TOKEN)
                Toast.makeText(this, ERROR_GET_FCM_TOKEN, Toast.LENGTH_SHORT).show()
                return@addOnCompleteListener
            }

            val token = fcmTask.result
            MyApplication.prefs.setPref(PREF_KEY_FCM_TOKEN, token)
        }
    }

    private fun initBottomNavigationView() {
        val navHost: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment? ?: return
        navController = navHost.navController

        binding.bnvBottomNav.apply {
            // set NavController.
            setupWithNavController(navController)

            // set BNV ItemSelectedListener.
            setOnItemSelectedListener {
                val isNotLogin =
                    !PreferencesManager(context).isLogin() &&
                            (it.itemId == R.id.myPageFragment || it.itemId == R.id.bookmarkFragment)

                if (isNotLogin) {
                    showLoginSignUpBottomSheet()
                    return@setOnItemSelectedListener false
                }

                NavigationUI.onNavDestinationSelected(it, navController)
                true
            }

            // set BNV ItemReselectedListener.
            setOnItemReselectedListener {
                navController.popBackStack(it.itemId, inclusive = false)
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bnvBottomNav.visibility =
                if (destination.id != R.id.homeFragment
                    && destination.id != R.id.myPageFragment
                    && destination.id != R.id.bookmarkFragment)
                    View.GONE
                else
                    View.VISIBLE
        }
    }

    private fun showLoginSignUpBottomSheet() {
        val btmSheet = LoginSignUpBottomSheet(
            object : LoginSignUpBottomSheet.LoginSignUpBottomSheetEventListener {
                override fun onClickLogin() {
                    navController.navigate(R.id.action_global_loginFragment)
                }

                override fun onClickSignUp() {
                    navController.navigate(R.id.action_global_signUpFragment)
                }
            })

        btmSheet.show(supportFragmentManager, LOGIN_SIGNUP_BOTTOM_SHEET)
    }
}