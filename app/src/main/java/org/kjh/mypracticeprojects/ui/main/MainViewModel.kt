package org.kjh.mypracticeprojects.ui

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.kjh.mypracticeprojects.MyApplication
import org.kjh.mypracticeprojects.PREF_KEY_LOGIN_ID
import org.kjh.mypracticeprojects.model.LocationItem
import org.kjh.mypracticeprojects.model.UserModel
import org.kjh.mypracticeprojects.repository.UserRepository
import org.kjh.mypracticeprojects.ui.main.mypage.MediaStoreImage
import org.kjh.mypracticeprojects.util.DataState
import org.kjh.mypracticeprojects.util.SingleLiveEvent
import javax.inject.Inject

/**
 * MyPracticeProjects
 * Class: MainViewModel
 * Created by mac on 2021/07/21.
 *
 * Description:
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _uploadImgData = MutableLiveData<MediaStoreImage?>()
    val uploadImgData: LiveData<MediaStoreImage?> = _uploadImgData

    private val _uploadLocationData = MutableLiveData<LocationItem?>()
    val uploadLocationData: LiveData<LocationItem?> = _uploadLocationData

    fun setUploadImgData(data: MediaStoreImage) {
        _uploadImgData.value = data
    }

    fun setUploadLocationData(data: LocationItem?) {
        _uploadLocationData.value = data
    }


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

    fun updateUserData(data: DataState<UserModel>) {
        _userData.value = data
    }
}