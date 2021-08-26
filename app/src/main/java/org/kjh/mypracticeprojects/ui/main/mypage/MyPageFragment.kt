package org.kjh.mypracticeprojects.ui.main.mypage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import org.kjh.mypracticeprojects.R
import org.kjh.mypracticeprojects.countryList
import org.kjh.mypracticeprojects.databinding.FragmentMypageBinding
import org.kjh.mypracticeprojects.ui.base.BaseFragment
import org.kjh.mypracticeprojects.ui.main.MainViewModel


@AndroidEntryPoint
class MyPageFragment :
    BaseFragment<FragmentMypageBinding>(R.layout.fragment_mypage) {

    private lateinit var _myPagePagerAdapter: MyPagePagerAdapter
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mainViewModel = mainViewModel

        initTabLayoutWithPager()
        initToolbarWithNavigation()
    }

    private fun initTabLayoutWithPager() {
        binding.pager.adapter = MyPagePagerAdapter(this)

        TabLayoutMediator(binding.tlTabLayout, binding.pager) { tab, position ->
            tab.text = countryList[position]
        }.attach()
    }

    private fun initToolbarWithNavigation() {
        binding.tbMypageToolbar.inflateMenu(R.menu.menu_mypage)

        val navController = findNavController()
        val appBarConfig = AppBarConfiguration(setOf(
            R.id.homeFragment,
            R.id.myPageFragment
        ))

        binding.tbMypageToolbar.apply {
            setupWithNavController(navController, appBarConfig)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.selectPictureFragment -> {
                        navController.navigate(R.id.action_thirdFragment_to_writeFragment)
                        true
                    }
                    R.id.settingFragment -> {
                        navController.navigate(R.id.action_thirdFragment_to_settingFragment)
                        true
                    }
                    else -> false
                }
            }
        }
    }
}

class MyPagePagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = countryList.size

    override fun createFragment(position: Int): Fragment =
        AreaImageFragment().apply {
            arguments = Bundle().apply {
                putString("City", countryList[position])
            }
        }
}