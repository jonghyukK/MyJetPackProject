package org.kjh.mypracticeprojects.ui.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.kjh.mypracticeprojects.*
import org.kjh.mypracticeprojects.databinding.FragmentLoginBinding
import org.kjh.mypracticeprojects.ui.base.BaseFragment
import org.kjh.mypracticeprojects.ui.main.MainViewModel
import org.kjh.mypracticeprojects.util.DataState

@AndroidEntryPoint
class LoginFragment
    : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    private val mainViewModel: MainViewModel by activityViewModels()
    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        initToolbarWithNavigation()
        subscribeObserver()
    }

    private fun initToolbarWithNavigation() {
        binding.tbLogin.setupWithNavController(findNavController())
    }

    private fun subscribeObserver() {
        viewModel.loginDataState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    MyApplication.prefs.setPref(PREF_KEY_LOGIN_STATE, LoginState.LOGIN.value)
                    MyApplication.prefs.setPref(PREF_KEY_LOGIN_ID, viewModel.email.value)

                    mainViewModel.reqMyUserData()
                    navigate(action = R.id.action_loginFragment_to_homeFragment)
                }
            }
        })
    }
}