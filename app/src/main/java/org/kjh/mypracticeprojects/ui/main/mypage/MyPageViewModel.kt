package org.kjh.mypracticeprojects.ui.main.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.kjh.mypracticeprojects.MyApplication
import org.kjh.mypracticeprojects.PREF_KEY_LOGIN_ID
import org.kjh.mypracticeprojects.model.DataResponse
import org.kjh.mypracticeprojects.model.UserModel
import org.kjh.mypracticeprojects.repository.UserRepository
import org.kjh.mypracticeprojects.util.DataState
import javax.inject.Inject

/**
 * MyPracticeProjects
 * Class: ThridViewModel
 * Created by mac on 2021/08/09.
 *
 * Description:
 */
@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    private val _userData = MutableLiveData<DataState<UserModel>?>()
    val userData: LiveData<DataState<UserModel>?> = _userData

    fun getMyUserData() {
        viewModelScope.launch {
            userRepository.reqUser(email = MyApplication.prefs.getPref(PREF_KEY_LOGIN_ID, ""))
                .onEach { dataState ->
                    _userData.value = dataState
                }
                .launchIn(viewModelScope)
        }
    }

    override fun onCleared() {
        super.onCleared()

        Logger.e("onCleared!!")
    }
}