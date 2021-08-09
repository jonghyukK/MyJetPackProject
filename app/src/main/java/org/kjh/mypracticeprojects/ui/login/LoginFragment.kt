package org.kjh.mypracticeprojects.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.orhanobut.logger.Logger
import dagger.hilt.android.AndroidEntryPoint
import org.kjh.mypracticeprojects.LOGIN
import org.kjh.mypracticeprojects.LOGIN_STATE
import org.kjh.mypracticeprojects.MyApplication
import org.kjh.mypracticeprojects.R
import org.kjh.mypracticeprojects.databinding.FragmentLoginBinding
import org.kjh.mypracticeprojects.model.DataResponse
import org.kjh.mypracticeprojects.ui.base.BaseFragment
import org.kjh.mypracticeprojects.ui.main.MainActivity
import org.kjh.mypracticeprojects.util.DataState

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        // Go To Sign Up.
        binding.tvSignup.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.next_action, null)
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeObserver()
    }

    private fun subscribeObserver() {
        viewModel.loginDataState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    MyApplication.prefs.setPref(LOGIN_STATE, LOGIN)
                    startActivity(Intent(activity, MainActivity::class.java))
                    (activity as LoginActivity).finish()
                }
            }
        })
    }
}