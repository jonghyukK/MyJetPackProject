package org.kjh.mypracticeprojects.ui.main.mypage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import org.kjh.mypracticeprojects.R
import org.kjh.mypracticeprojects.databinding.FragmentMypageBinding
import org.kjh.mypracticeprojects.ui.base.BaseFragment


@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMypageBinding>(R.layout.fragment_mypage) {

    private val viewModel: MyPageViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

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
                    R.id.writeFragment -> {
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