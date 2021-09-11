package org.kjh.mypracticeprojects.ui.main.postdetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import org.kjh.mypracticeprojects.R
import org.kjh.mypracticeprojects.databinding.FragmentPostDetailBinding
import org.kjh.mypracticeprojects.model.PostModel
import org.kjh.mypracticeprojects.ui.base.BaseFragment

private const val ARG_PARAM2 = "postItem"

private val TAB_NAMES = listOf("데이로그", "정보")

@AndroidEntryPoint
class PostDetailFragment: BaseFragment<FragmentPostDetailBinding>(R.layout.fragment_post_detail) {

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