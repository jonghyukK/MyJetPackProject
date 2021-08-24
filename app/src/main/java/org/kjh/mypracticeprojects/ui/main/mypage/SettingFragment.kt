package org.kjh.mypracticeprojects.ui.main.mypage

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import org.kjh.mypracticeprojects.*
import org.kjh.mypracticeprojects.databinding.FragmentSettingBinding
import org.kjh.mypracticeprojects.ui.base.BaseFragment
import org.kjh.mypracticeprojects.ui.login.LoginActivity
import org.kjh.mypracticeprojects.ui.main.MainActivity

class SettingFragment : BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        val appBarConfig = AppBarConfiguration(navController.graph)

        binding.tbSettingToolbar.setupWithNavController(navController, appBarConfig)

        binding.btnLogout.setOnClickListener {
            MyApplication.prefs.setPref(PREF_KEY_LOGIN_ID, "")
            MyApplication.prefs.setPref(PREF_KEY_LOGIN_STATE, LoginState.LOGOUT.value)
            startActivity(Intent(view.rootView.context, LoginActivity::class.java))
            (activity as MainActivity).finish()
        }
    }
}