package org.kjh.mypracticeprojects.ui.login

import android.widget.EditText
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.kjh.mypracticeprojects.MyApplication
import org.kjh.mypracticeprojects.PREF_KEY_FCM_TOKEN
import org.kjh.mypracticeprojects.isValidPattern
import org.kjh.mypracticeprojects.model.UserResponse
import org.kjh.mypracticeprojects.repository.UserRepository
import org.kjh.mypracticeprojects.ui.login.SignUpFragment.Companion.TAG_EMAIL
import org.kjh.mypracticeprojects.ui.login.SignUpFragment.Companion.TAG_PW
import org.kjh.mypracticeprojects.ui.login.SignUpFragment.Companion.TAG_PwConfirm
import org.kjh.mypracticeprojects.util.DataState
import javax.inject.Inject

/**
 * MyPracticeProjects
 * Class: SignUpViewModel
 * Created by mac on 2021/07/22.
 *
 * Description:
 */

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel(), FocusEventHandler {

    val email     : MutableLiveData<String> = MutableLiveData()
    val pw        : MutableLiveData<String> = MutableLiveData()
    val pwConfirm : MutableLiveData<String> = MutableLiveData()

    private val _emailValidState: MutableLiveData<ValidateState> = MutableLiveData()
    val emailValidState: LiveData<ValidateState> = _emailValidState

    private val _pwValidState: MutableLiveData<ValidateState> = MutableLiveData()
    val pwValidState: LiveData<ValidateState> = _pwValidState

    private val _pwConfirmValidState: MutableLiveData<ValidateState> = MutableLiveData()
    val pwConfirmValidState: LiveData<ValidateState> = _pwConfirmValidState

    private val _signUpDataState: MutableLiveData<DataState<UserResponse>> = MutableLiveData()
    val signUpDataState: LiveData<DataState<UserResponse>> = _signUpDataState

    // API - Check Validate Email.
    private fun requestDuplicateCheckEmail() {
        viewModelScope.launch {
            userRepository.reqValidateEmail(
                email = email.value.toString()
            )
                .onEach { dataState ->
                    when (dataState) {
                        is DataState.Success ->
                            _emailValidState.value = ValidateState.SUCCESS
                        is DataState.Error ->
                            _emailValidState.value = ValidateState.ERROR_DUPLICATED
                    }
                }.launchIn(viewModelScope)
        }
    }

    // API - Request SignUp.
    private fun requestSignUp() {
        val userToken = MyApplication.prefs.getPref(PREF_KEY_FCM_TOKEN, "")

        viewModelScope.launch {
            userRepository.reqSignUp(
                email = email.value!!,
                pw    = pw.value!!,
                token = userToken,
            )
                .onEach { dataState ->
                    _signUpDataState.value = dataState
                }
                .launchIn(viewModelScope)
        }
    }

    // Check Duplicate for Email.
    fun checkPatternEmail() {
        if (!email.value.toString().isValidPattern()) {
            _emailValidState.value = ValidateState.ERROR_PATTERN
            return
        }

        requestDuplicateCheckEmail()
    }

    // Check Pw <-> PwConfirm Before SignUp.
    fun checkPwMatch() {
        if (pw.value?.length in 1..7) {
            _pwValidState.value = ValidateState.ERROR_LENGTH
        } else if (!pw.value.equals(pwConfirm.value)) {
            _pwConfirmValidState.value = ValidateState.ERROR_NOT_MATCH
        } else {
            requestSignUp()
        }
    }

    override fun onFocusLost(view: EditText) {
        _pwValidState.value =
            if (view.text.length >= 8) ValidateState.SUCCESS else ValidateState.ERROR_LENGTH
    }

    fun clearErrorWhenTextChanged(s: CharSequence?, tag: String) {
        when (tag) {
            TAG_EMAIL -> if (_emailValidState.value != ValidateState.INIT)
                _emailValidState.value = ValidateState.INIT

            TAG_PW, TAG_PwConfirm -> {
                if (_pwConfirmValidState.value != ValidateState.INIT)
                    _pwConfirmValidState.value = ValidateState.INIT
                if (_pwValidState.value != ValidateState.INIT)
                    _pwValidState.value = ValidateState.INIT
            }
        }
    }
}