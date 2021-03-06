package org.kjh.mypracticeprojects.ui.main.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * MyPracticeProjects
 * Class: PostListViewModel
 * Created by mac on 2021/09/11.
 *
 * Description:
 */
class PostListViewModel: ViewModel() {
    private val _postListViewType = MutableLiveData(POST_TYPE_SMALL)
    val postListViewType: LiveData<Int> = _postListViewType

    fun setViewType(type: Int) {
        if (_postListViewType.value != type) {
            _postListViewType.value = type
        }
    }
}