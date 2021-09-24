package org.kjh.mypracticeprojects.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.kjh.mypracticeprojects.*
import org.kjh.mypracticeprojects.model.DataResponse
import org.kjh.mypracticeprojects.repository.UserRepository
import org.kjh.mypracticeprojects.util.DataState
import javax.inject.Inject

/**
 * MyPracticeProjects
 * Class: LoginViewModel
 * Created by mac on 2021/08/03.
 *
 * Description:
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    val email: MutableLiveData<String> = MutableLiveData()
    val pw   : MutableLiveData<String> = MutableLiveData()

    private val _emailValidState: MutableLiveData<ValidateState> = MutableLiveData()
    val emailValidState: LiveData<ValidateState> = _emailValidState

    private val _pwValidState: MutableLiveData<ValidateState> = MutableLiveData()
    val pwValidState: LiveData<ValidateState> = _pwValidState

    private val _loginDataState: MutableLiveData<DataState<DataResponse>> = MutableLiveData()
    val loginDataState: LiveData<DataState<DataResponse>> = _loginDataState

    // API Result - Login API.
    private fun requestLogin() {
        val userToken = MyApplication.prefs.getPref(PREF_KEY_FCM_TOKEN, "")
        viewModelScope.launch {
            userRepository.reqLogin(
                email = email.value.toString(),
                pw    = pw.value.toString(),
                token = userToken
            )
                .onEach { dataState ->
                    when (dataState) {
                        is DataState.Success ->
                            _loginDataState.value = dataState
                        is DataState.Error ->
                            setError(dataState.exception.message)
                    }
                }
                .launchIn(viewModelScope)
        }
    }

    private fun setError(errorMsg: String?) {
        when (errorMsg) {
            ERROR_NOTHING_EMAIL -> _emailValidState.value = ValidateState.ERROR_EMAIL_NOTHING
            ERROR_WRONG_PW      -> _pwValidState.value = ValidateState.ERROR_PW
        }
    }

    fun clearErrorWhenTextChanged(s: CharSequence?) {
        if (_emailValidState.value != ValidateState.INIT) {
            _emailValidState.value = ValidateState.INIT
        }
        if (_pwValidState.value != ValidateState.INIT) {
            _pwValidState.value = ValidateState.INIT
        }
    }

    fun checkLoginData() {
        if (!email.value.toString().isValidPattern()) {
            _emailValidState.value = ValidateState.ERROR_PATTERN
            return
        }

        requestLogin()
    }
}