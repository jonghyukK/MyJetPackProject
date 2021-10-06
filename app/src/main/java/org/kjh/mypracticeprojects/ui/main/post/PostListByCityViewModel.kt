package org.kjh.mypracticeprojects.ui.main.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.kjh.mypracticeprojects.model.PostModel
import org.kjh.mypracticeprojects.repository.PostRepository
import org.kjh.mypracticeprojects.util.DataState
import javax.inject.Inject

/**
 * MyPracticeProjects
 * Class: PostListByCityViewModel
 * Created by mac on 2021/09/07.
 *
 * Description:
 */
@HiltViewModel
class PostListByCityViewModel @Inject constructor(
    private val postRepository: PostRepository
): ViewModel() {

    private val _postListViewType = MutableLiveData(POST_TYPE_LARGE)
    val postListViewType: LiveData<Int> = _postListViewType

    private val _postListByCity = MutableLiveData<DataState<List<PostModel>>>()
    val postListByCity: LiveData<DataState<List<PostModel>>> = _postListByCity

    fun getPostListByCity(cityName: String) {
        viewModelScope.launch {
            postRepository.getPosts(cityName = cityName)
                .onEach { dataState ->
                    _postListByCity.value = dataState
                }
                .launchIn(viewModelScope)
        }
    }

    fun setViewType(type: Int) {
        if (_postListViewType.value != type) {
            _postListViewType.value = type
        }
    }
}