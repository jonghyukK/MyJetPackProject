package org.kjh.mypracticeprojects.ui.main

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.orhanobut.logger.Logger
import dagger.hilt.android.AndroidEntryPoint
import org.kjh.mypracticeprojects.R
import org.kjh.mypracticeprojects.databinding.ActivityMainBinding
import org.kjh.mypracticeprojects.ui.base.BaseActivity

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

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
        binding.bnvBottomNav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.settingFragment ||
                destination.id == R.id.writeFragment) {
                binding.bnvBottomNav.visibility = View.GONE
            } else {
                binding.bnvBottomNav.visibility = View.VISIBLE
            }
        }
    }
}