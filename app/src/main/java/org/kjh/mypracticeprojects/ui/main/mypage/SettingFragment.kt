package org.kjh.mypracticeprojects.ui.main.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import org.kjh.mypracticeprojects.R
import org.kjh.mypracticeprojects.databinding.FragmentSettingBinding
import org.kjh.mypracticeprojects.ui.base.BaseFragment

class SettingFragment : BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        val appBarConfig = AppBarConfiguration(navController.graph)

        binding.tbSettingToolbar.setupWithNavController(navController, appBarConfig)
    }
}