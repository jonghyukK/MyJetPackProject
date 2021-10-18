package org.kjh.mypracticeprojects.ui.main.home

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
 * Class: HomeViewModel
 * Created by mac on 2021/09/04.
 *
 * Description:
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val postRepository: PostRepository
) : ViewModel() {

    private val _recentPostList = MutableLiveData<DataState<List<PostModel>>>()
    val recentPostList: LiveData<DataState<List<PostModel>>> = _recentPostList

    init {
        getRecentPostList()
    }

    fun getRecentPostList() {
        viewModelScope.launch {
            postRepository.getPosts()
                .onEach { dataState ->
                    _recentPostList.value = dataState
                }
                .launchIn(viewModelScope)
        }
    }
}