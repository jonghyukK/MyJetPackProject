package org.kjh.mypracticeprojects.ui.login

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.kjh.mypracticeprojects.model.DataResponse
import org.kjh.mypracticeprojects.repository.UserRepository
import org.kjh.mypracticeprojects.util.DataState
import org.kjh.mypracticeprojects.util.SingleLiveEvent
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

    // API Result - Login API.
    private val _dataState: MutableLiveData<DataState<DataResponse>> = MutableLiveData()
    val dataState: LiveData<DataState<DataResponse>>
        get() = _dataState

    private val _isEmailValid: MutableLiveData<Boolean> = MutableLiveData()
    val isEmailValid: LiveData<Boolean>
        get() = _isEmailValid

    private val _isPwValid: MutableLiveData<Boolean> = MutableLiveData()
    val isPwValid: LiveData<Boolean>
        get() = _isPwValid

    private val _isValidInputs: MutableLiveData<Boolean> = MutableLiveData()
    val isValidInputs: LiveData<Boolean>
        get() = _isValidInputs

    private fun validation() {
        _isValidInputs.value =
            isEmailValid.value ?: false && isPwValid.value ?: false
    }

    fun requestLogin() {
        viewModelScope.launch {
            userRepository.reqLogin(email.value.toString(), pw.value.toString())
                .onEach { dataState ->
                    _dataState.value = dataState
                }
                .launchIn(viewModelScope)
        }
    }

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
}