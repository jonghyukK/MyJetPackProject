package org.kjh.mypracticeprojects.ui.main.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.kjh.mypracticeprojects.ui.main.POST_TYPE_SMALL

/**
 * MyPracticeProjects
 * Class: BookmarkViewModel
 * Created by mac on 2021/09/16.
 *
 * Description:
 */

class BookmarkViewModel: ViewModel() {

    private val _bookmarkViewType = MutableLiveData(POST_TYPE_SMALL)
    val bookmarkViewType: LiveData<Int> = _bookmarkViewType

    fun setViewType(type: Int) {
        if (_bookmarkViewType.value != type) {
            _bookmarkViewType.value = type
        }
    }
}