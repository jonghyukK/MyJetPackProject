package org.kjh.mypracticeprojects.ui.main.postdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.kjh.mypracticeprojects.MyApplication
import org.kjh.mypracticeprojects.PREF_KEY_LOGIN_ID
import org.kjh.mypracticeprojects.model.PostModel
import org.kjh.mypracticeprojects.model.UserModel
import org.kjh.mypracticeprojects.repository.PostRepository
import org.kjh.mypracticeprojects.repository.UserRepository
import org.kjh.mypracticeprojects.util.DataState
import javax.inject.Inject

/**
 * MyPracticeProjects
 * Class: PostDetailViewModel
 * Created by mac on 2021/09/08.
 *
 * Description:
 */
@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository
): ViewModel() {

    private val _postListByPlace = MutableLiveData<DataState<List<PostModel>>>()
    val postListByPlace: LiveData<DataState<List<PostModel>>> = _postListByPlace

    private val _resultUpdateBookMark = MutableLiveData<DataState<UserModel>>()
    val resultUpdateBookMark : LiveData<DataState<UserModel>> = _resultUpdateBookMark

    fun getPostsByPlaceName(placeName: String) {
        viewModelScope.launch {
            postRepository.getPosts(placeName = placeName)
                .onEach { dataState ->
                    _postListByPlace.value = dataState
                }
                .launchIn(viewModelScope)
        }
    }

    fun updateUserBookMark(
        postId: Int,
        placeName: String
    ) {
        val email = MyApplication.prefs.getPref(PREF_KEY_LOGIN_ID, "")

        viewModelScope.launch {
            userRepository.updateUserBookMark(
                email = email,
                postId = postId,
                placeName = placeName
            ).onEach { dataState ->
                _resultUpdateBookMark.value = dataState
            }
                .launchIn(viewModelScope)
        }
    }
}