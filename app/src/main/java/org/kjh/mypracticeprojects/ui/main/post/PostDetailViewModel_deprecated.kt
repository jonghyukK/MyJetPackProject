package org.kjh.mypracticeprojects.ui.main.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.kjh.mypracticeprojects.model.PostResponse
import org.kjh.mypracticeprojects.model.UserModel
import org.kjh.mypracticeprojects.repository.PostRepository
import org.kjh.mypracticeprojects.util.DataState
import javax.inject.Inject

/**
 * MyPracticeProjects
 * Class: PostDetailViewModel
 * Created by mac on 2021/09/01.
 *
 * Description:
 */

@HiltViewModel
class PostDetailViewModel_deprecated @Inject constructor(
    private val postRepository: PostRepository
): ViewModel() {

    private val _deleteResult = MutableLiveData<DataState<UserModel>>()
    val deleteResult: LiveData<DataState<UserModel>> = _deleteResult

    fun deletePost(
        postId: Int,
        email : String
    ) {
        viewModelScope.launch {
            postRepository.deletePost(
                postId = postId,
                email  = email
            )
                .onEach { dataState ->
                    _deleteResult.value = dataState
                }
                .launchIn(viewModelScope)
        }
    }
}