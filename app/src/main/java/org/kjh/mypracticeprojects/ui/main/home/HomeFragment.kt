package org.kjh.mypracticeprojects.ui.main.home

import android.os.Bundle
import android.view.View
import androidx.navigation.NavHost
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import org.kjh.mypracticeprojects.R
import org.kjh.mypracticeprojects.databinding.FragmentHomeBinding
import org.kjh.mypracticeprojects.ui.base.BaseFragment


class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        val appBarConfig = AppBarConfiguration(setOf(
            R.id.homeFragment,
            R.id.myPageFragment
        ))

        binding.tbHomeToolbar.setupWithNavController(navController, appBarConfig)
    }
}