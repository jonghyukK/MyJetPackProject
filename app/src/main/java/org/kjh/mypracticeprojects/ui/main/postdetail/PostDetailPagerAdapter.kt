package org.kjh.mypracticeprojects.ui.main.postdetail

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * MyPracticeProjects
 * Class: PostDetailPagerAdapter
 * Created by mac on 2021/09/08.
 *
 * Description:
 */
class PostDetailPagerAdapter(
    frag: Fragment
): FragmentStateAdapter(frag) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> PostDetailDayLogFragment()
            else -> PostDetailInfoFragment()
        }
}