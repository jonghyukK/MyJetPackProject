package org.kjh.mypracticeprojects.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.kjh.mypracticeprojects.R
import org.kjh.mypracticeprojects.databinding.BottomSheetLoginSignupBinding

/**
 * MyPracticeProjects
 * Class: LoginSignUpBottomSheet
 * Created by mac on 2021/09/11.
 *
 * Description:
 */
class LoginSignUpBottomSheet(
    private val onClickLogin : () -> Unit,
    private val onClickSignUp: () -> Unit
): BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetLoginSignupBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_login_signup, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnGoToLogin.setOnClickListener {
            onClickLogin()
            dismiss()
        }

        binding.btnGoToSignUp.setOnClickListener {
            onClickSignUp()
            dismiss()
        }
    }
}