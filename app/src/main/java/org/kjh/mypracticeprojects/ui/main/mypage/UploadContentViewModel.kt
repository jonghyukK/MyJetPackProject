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
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.kjh.mypracticeprojects.MyApplication
import org.kjh.mypracticeprojects.PREF_KEY_LOGIN_ID
import org.kjh.mypracticeprojects.model.LocationItem
import org.kjh.mypracticeprojects.model.UserModel
import org.kjh.mypracticeprojects.repository.UserRepository
import org.kjh.mypracticeprojects.util.DataState
import java.io.File
import javax.inject.Inject

/**
 * MyPracticeProjects
 * Class: UploadCOntentViewModel
 * Created by mac on 2021/08/24.
 *
 * Description:
 */
@HiltViewModel
class UploadContentViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    val content = MutableLiveData<String>()

    private val _uploadResult = MutableLiveData<DataState<UserModel>>()
    val uploadResult: LiveData<DataState<UserModel>> = _uploadResult

    fun uploadContent(imgData: MediaStoreImage, locationData: LocationItem) {
        val file = File(imgData.realPath)
        val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
        val email = MyApplication.prefs.getPref(PREF_KEY_LOGIN_ID, "")

        viewModelScope.launch {
            userRepository.uploadPost(
                email,
                content.value.toString(),
                body,
                locationData
            ).onEach { dataState ->
                _uploadResult.value = dataState
            }.launchIn(viewModelScope)
        }
    }
}