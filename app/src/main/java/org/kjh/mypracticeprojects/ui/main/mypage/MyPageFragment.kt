package org.kjh.mypracticeprojects.ui.main.mypage

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import org.kjh.mypracticeprojects.R
import org.kjh.mypracticeprojects.databinding.FragmentMypageBinding
import org.kjh.mypracticeprojects.navigate
import org.kjh.mypracticeprojects.ui.base.BaseFragment
import org.kjh.mypracticeprojects.ui.main.MainViewModel


@AndroidEntryPoint
class MyPageFragment :
    BaseFragment<FragmentMypageBinding>(R.layout.fragment_mypage) {

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainViewModel = mainViewModel

        initTabLayoutWithPager()
        initToolbarWithNavigation()

        binding.btnEditProfile.setOnClickListener {
            val myProfileImg = mainViewModel.myUserData.value?.let {
                it.successData()?.profileImg
            }
            navigate(
                action = R.id.action_myPageFragment_to_profileEditFragment,
                bundle = bundleOf("profileImg" to myProfileImg)
            )
        }
    }

    private fun initTabLayoutWithPager() {
        binding.pager.adapter = MyPagePagerAdapter(this)
        TabLayoutMediator(binding.tlTabLayout, binding.pager) { tab, _ ->
            tab.text = getString(R.string.my_travel)
        }.attach()
    }

    private fun initToolbarWithNavigation() {
        val navController = findNavController()
        val appBarConfig = AppBarConfiguration(setOf(
            R.id.homeFragment,
            R.id.myPageFragment
        ))

        binding.tbMypageToolbar.apply {
            inflateMenu(R.menu.menu_mypage)
            setupWithNavController(navController, appBarConfig)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.selectPictureFragment -> {
                        navController.navigate(R.id.action_myPageFragment_to_selectPictureFragment)
                        true
                    }
                    R.id.settingFragment -> {
                        navController.navigate(R.id.action_myPageFragment_to_settingFragment)
                        true
                    }
                    else -> false
                }
            }
        }
    }
}

class MyPagePagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 1
    override fun createFragment(position: Int): Fragment = MyPostsByCityFragment()
}