package org.kjh.mypracticeprojects.ui.main.mypage

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.kjh.mypracticeprojects.repository.UserRepository
import javax.inject.Inject

/**
 * MyPracticeProjects
 * Class: ThridViewModel
 * Created by mac on 2021/08/09.
 *
 * Description:
 */
@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

}