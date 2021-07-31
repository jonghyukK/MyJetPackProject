package org.kjh.mypracticeprojects.ui.login

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Patterns
import androidx.lifecycle.*
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.kjh.mypracticeprojects.ERROR_EMAIL_EMPTY
import org.kjh.mypracticeprojects.ERROR_PW_CONFIRM_EMPTY
import org.kjh.mypracticeprojects.ERROR_PW_EMPTY
import org.kjh.mypracticeprojects.ERROR_PW_NOT_MATCH_WITH_CONFIRM
import org.kjh.mypracticeprojects.model.UserModel
import org.kjh.mypracticeprojects.model.UserResponse
import org.kjh.mypracticeprojects.repository.UserRepository
import org.kjh.mypracticeprojects.util.DataState
import java.lang.Exception
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
    private val userRepository: UserRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val email     : MutableLiveData<String> = MutableLiveData()
    val pw        : MutableLiveData<String> = MutableLiveData()
    val pwConfirm : MutableLiveData<String> = MutableLiveData()

    // API Result - SignUp API.
    private val _dataState: MutableLiveData<DataState<UserResponse>> = MutableLiveData()
    val dataState: LiveData<DataState<UserResponse>>
        get() = _dataState

    // isValid SignUp - Email, Pw, PwConfirm
    private val _isValid: MutableLiveData<Boolean> = MutableLiveData()
    val isValid: LiveData<Boolean>
        get() = _isValid

    // isValid Email
    private val _isEmailValid: MutableLiveData<Boolean> = MutableLiveData()
    val isEmailValid: LiveData<Boolean>
        get() = _isEmailValid

    // isValid Pw
    private val _isPwValid: MutableLiveData<Boolean> = MutableLiveData()
    val isPwValid: LiveData<Boolean>
        get() = _isPwValid

    // isValid PwConfirm
    private val _isPwConfirmValid: MutableLiveData<Boolean> = MutableLiveData()
    val isPwConfirmValid: LiveData<Boolean>
        get() = _isPwConfirmValid

    fun requestSignUp() {
        viewModelScope.launch {
            userRepository.reqSignUp(
                UserModel(
                    email = email.value,
                    pw = pw.value,
                    pwConfirm = pwConfirm.value
                ))
                .onEach { dataState ->
                    Logger.d("$dataState")
                    _dataState.value = dataState
                }
                .launchIn(viewModelScope)
        }
    }

    private fun validation() {
        _isValid.value =
            isEmailValid.value ?: false
                    && isPwValid.value ?: false
                    && isPwConfirmValid.value ?: false
    }


    // TextWatcher - Email
    val emailWatcher: TextWatcher = object: TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            _isEmailValid.value =
                !TextUtils.isEmpty(s) && Patterns.EMAIL_ADDRESS.matcher(s).matches()
        }
        override fun afterTextChanged(s: Editable?) {
            validation()
        }
    }

    // TextWatcher - Password
    val pwWatcher: TextWatcher = object: TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            _isPwValid.value =
                !TextUtils.isEmpty(s) && s != null && s.length >= 8
        }
        override fun afterTextChanged(s: Editable?) {
            validation()
        }
    }

    // TextWatcher - Password Confirm
    val pwConfirmWatcher: TextWatcher = object: TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            _isPwConfirmValid.value =
                !TextUtils.isEmpty(s) && s != null && pw.value == s.toString()
        }
        override fun afterTextChanged(s: Editable?) {
            validation()
        }
    }
}