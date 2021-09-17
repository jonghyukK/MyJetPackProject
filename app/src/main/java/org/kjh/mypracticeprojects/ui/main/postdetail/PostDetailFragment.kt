package org.kjh.mypracticeprojects.ui.main.postdetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.orhanobut.logger.Logger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.kjh.mypracticeprojects.R
import org.kjh.mypracticeprojects.databinding.FragmentPostDetailBinding
import org.kjh.mypracticeprojects.model.PostModel
import org.kjh.mypracticeprojects.ui.base.BaseFragment
import org.kjh.mypracticeprojects.ui.main.MainViewModel
import org.kjh.mypracticeprojects.util.DataState

private const val POST_ITEM = "postItem"

private val TAB_NAMES = listOf("데이로그", "정보")

@AndroidEntryPoint
class PostDetailFragment: BaseFragment<FragmentPostDetailBinding>(R.layout.fragment_post_detail) {

    private val mainViewModel: MainViewModel by activityViewModels()
    private val viewModel: PostDetailViewModel by activityViewModels()
    private lateinit var argPostItem: PostModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            argPostItem = it.get(POST_ITEM) as PostModel
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPostsByPlaceName(argPostItem.placeName)

        initTabLayoutWithViewPager2()
        initToolbarWithNavigation()

        mainViewModel.myUserData.observe(viewLifecycleOwner, { data ->
            data?.bookMarks?.let {
                val hasBookMarkHere = it.any { it.placeName == argPostItem.placeName}

                if (hasBookMarkHere) {
                    binding.tbPostDetailToolbar.menu.getItem(0).setIcon(R.drawable.ic_bookmark_on)
                } else {
                    binding.tbPostDetailToolbar.menu.getItem(0).setIcon(R.drawable.ic_bookmark_none)
                }
            }
        })
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
        binding.ctlPostDetailCollaps.title = argPostItem.placeName

        binding.tbPostDetailToolbar.apply {
            inflateMenu(R.menu.menu_bookmark)
            setupWithNavController(findNavController())
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menu_bookmark -> {
                        mainViewModel.updateUserBookMark(
                            argPostItem.postId,
                            argPostItem.placeName
                        )
                        true
                    }
                    else -> false
                }
            }
        }
    }
}