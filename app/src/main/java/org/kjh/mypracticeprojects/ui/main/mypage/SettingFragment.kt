package org.kjh.mypracticeprojects.ui.main.mypage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import org.kjh.mypracticeprojects.*
import org.kjh.mypracticeprojects.databinding.FragmentSettingBinding
import org.kjh.mypracticeprojects.ui.base.BaseFragment
import org.kjh.mypracticeprojects.ui.main.MainViewModel

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting) {

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        val appBarConfig = AppBarConfiguration(navController.graph)

        binding.tbSettingToolbar.setupWithNavController(navController, appBarConfig)

        binding.btnLogout.setOnClickListener {
            MyApplication.prefs.setPref(PREF_KEY_LOGIN_ID, "")
            MyApplication.prefs.setPref(PREF_KEY_LOGIN_STATE, LoginState.LOGOUT.value)

            mainViewModel.clearMyUserData()

            navigate(action = R.id.action_settingFragment_to_homeFragment)
        }
    }
}