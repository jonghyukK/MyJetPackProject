package org.kjh.mypracticeprojects.ui.main

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.kjh.mypracticeprojects.MyApplication
import org.kjh.mypracticeprojects.PREF_KEY_LOGIN_ID
import org.kjh.mypracticeprojects.model.LocationItem
import org.kjh.mypracticeprojects.model.UserModel
import org.kjh.mypracticeprojects.repository.PostRepository
import org.kjh.mypracticeprojects.repository.UserRepository
import org.kjh.mypracticeprojects.ui.main.mypage.MediaStoreImage
import org.kjh.mypracticeprojects.util.DataState
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
): ViewModel() {

    private val _uploadImgData = MutableLiveData<MediaStoreImage?>()
    val uploadImgData: LiveData<MediaStoreImage?> = _uploadImgData

    private val _uploadLocationData = MutableLiveData<LocationItem?>()
    val uploadLocationData: LiveData<LocationItem?> = _uploadLocationData

    private val _multipleImages = MutableLiveData<List<MediaStoreImage>?>()
    val multipleImages: LiveData<List<MediaStoreImage>?> = _multipleImages

    fun setUploadImgData(data: MediaStoreImage) { _uploadImgData.value = data }
    fun setUploadLocationData(data: LocationItem?) { _uploadLocationData.value = data }
    fun setMultipleImages(data: List<MediaStoreImage>) { _multipleImages.value = data }

    private val _myUserData = MutableLiveData<DataState<UserModel>?>()
    val myUserData: LiveData<DataState<UserModel>?> = _myUserData

    init {
        reqMyUserData()
    }

    fun reqMyUserData() {
        viewModelScope.launch {
            userRepository.reqUser(
                email = MyApplication.prefs.getPref(PREF_KEY_LOGIN_ID, "")
            )
                .onEach { result -> _myUserData.value = result }
                .launchIn(viewModelScope)
        }
    }

    fun updateUserBookMark(
        postId: Int,
        placeName: String
    ) {
        viewModelScope.launch {
            userRepository.updateUserBookMark(
                email     = MyApplication.prefs.getPref(PREF_KEY_LOGIN_ID, ""),
                postId    = postId,
                placeName = placeName
            )
                .onEach { result -> _myUserData.value = result }
                .launchIn(viewModelScope)
        }
    }

    fun updateMyUserData(userData: DataState<UserModel>) {
        _myUserData.value = userData
    }

    fun clearMyUserData() {
        _myUserData.value = null
    }
}