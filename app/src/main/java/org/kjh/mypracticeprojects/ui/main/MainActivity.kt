package org.kjh.mypracticeprojects.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.orhanobut.logger.Logger
import dagger.hilt.android.AndroidEntryPoint
import org.kjh.mypracticeprojects.*
import org.kjh.mypracticeprojects.databinding.ActivityMainBinding
import org.kjh.mypracticeprojects.ui.base.BaseActivity
import org.kjh.mypracticeprojects.ui.main.home.LoginSignUpBottomSheet

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBottomNavigationView()
    }

    private fun initBottomNavigationView() {
        val navHost: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment? ?: return
        navController = navHost.navController

        // Setup Bottom Navigation View
        with (binding.bnvBottomNav) {
            setupWithNavController(navController)

            setOnItemSelectedListener {
                if (it.itemId == R.id.myPageFragment &&
                    MyApplication.prefs.getPref(PREF_KEY_LOGIN_STATE, 0) == LoginState.LOGOUT.value) {
                    showLoginSignUpBottomSheet()
                    return@setOnItemSelectedListener false
                }

                NavigationUI.onNavDestinationSelected(it, navController)
                true
            }

            setOnItemReselectedListener {
                navController.popBackStack(it.itemId, inclusive = false)
            }
        }

        navController.addOnDestinationChangedListener { controller, destination, _ ->
            binding.bnvBottomNav.visibility =
                if (destination.id != R.id.homeFragment
                    && destination.id != R.id.myPageFragment)
                    View.GONE
                else
                    View.VISIBLE
        }

        navHost.childFragmentManager.addOnBackStackChangedListener {
            Logger.d("${navHost.childFragmentManager.backStackEntryCount}")
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

        btmSheet.show(supportFragmentManager, "tag")
    }
}