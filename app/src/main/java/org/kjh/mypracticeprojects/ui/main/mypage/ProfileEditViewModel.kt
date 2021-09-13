package org.kjh.mypracticeprojects.ui.main.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.kjh.mypracticeprojects.MyApplication
import org.kjh.mypracticeprojects.PREF_KEY_LOGIN_ID
import org.kjh.mypracticeprojects.model.UserModel
import org.kjh.mypracticeprojects.repository.UserRepository
import org.kjh.mypracticeprojects.util.DataState
import java.io.File
import javax.inject.Inject

/**
 * MyPracticeProjects
 * Class: ProfileEditViewModel
 * Created by mac on 2021/09/13.
 *
 * Description:
 */
@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    private val _resultProfileEdit = MutableLiveData<DataState<UserModel>>()
    val resultProfileEdit: LiveData<DataState<UserModel>> = _resultProfileEdit

    fun updateProfile(filePath: String) {
        val file = File(filePath)
        val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

        val email = MyApplication.prefs.getPref(PREF_KEY_LOGIN_ID, "")

        viewModelScope.launch {
            userRepository.updateUser(
                file = body,
                email = email
            ).onEach { dataState ->
                _resultProfileEdit.value = dataState
            }.launchIn(viewModelScope)
        }
    }
}