package org.kjh.mypracticeprojects.ui.main.postdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import org.kjh.mypracticeprojects.R
import org.kjh.mypracticeprojects.databinding.FragmentPostDetail2Binding
import org.kjh.mypracticeprojects.model.PostModel
import org.kjh.mypracticeprojects.ui.base.BaseFragment
import org.kjh.mypracticeprojects.util.DataState

private const val ARG_PARAM2 = "postItem"

private val TAB_NAMES = listOf("데이로그", "정보")

@AndroidEntryPoint
class PostDetailFragment2: BaseFragment<FragmentPostDetail2Binding>(R.layout.fragment_post_detail2) {

    private val viewModel: PostDetailViewModel by activityViewModels()
    private lateinit var argPostItem: PostModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            argPostItem = it.get(ARG_PARAM2) as PostModel
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPostsByPlaceName(argPostItem.placeName)

        initTabLayoutWithViewPager2()
        initToolbarWithNavigation()
    }

    private fun initTabLayoutWithViewPager2() {
        binding.vpPager.adapter = PostDetailPagerAdapter(this)
        binding.vpPager.offscreenPageLimit = 2
        binding.vpPager.isUserInputEnabled = false
        TabLayoutMediator(binding.tlTabLayout, binding.vpPager) { tab, position ->
            tab.text = TAB_NAMES[position]
        }.attach()
    }

    private fun initToolbarWithNavigation() {
        binding.ctlPostDetailCollaps.title = "Post Detail"
        binding.tbPostDetailToolbar.setupWithNavController(findNavController())
    }
}