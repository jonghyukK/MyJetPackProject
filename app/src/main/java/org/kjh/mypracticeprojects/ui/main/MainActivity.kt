package org.kjh.mypracticeprojects.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
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

    private val viewModel: MainViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBottomNavigationView()
        viewModel.reqMyUserData()
    }

    private fun initBottomNavigationView() {
        val navHost: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment? ?: return
        navController = navHost.navController

        // Setup Bottom Navigation View
        binding.bnvBottomNav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.settingFragment
                || destination.id == R.id.selectPictureFragment
                || destination.id == R.id.uploadContentFragment
                || destination.id == R.id.mapFragment
                || destination.id == R.id.postDetailFragment
                || destination.id == R.id.mapInfoFragment
                || destination.id == R.id.postListFragment
                || destination.id == R.id.postListByCityFragment
                || destination.id == R.id.postDetailFragment2
            ) {
                binding.bnvBottomNav.visibility = View.GONE
            } else {
                binding.bnvBottomNav.visibility = View.VISIBLE
            }
        }
    }
}