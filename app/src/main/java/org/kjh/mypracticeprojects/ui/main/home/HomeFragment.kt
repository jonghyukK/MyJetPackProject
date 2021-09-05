package org.kjh.mypracticeprojects.ui.main.home

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.orhanobut.logger.Logger
import dagger.hilt.android.AndroidEntryPoint
import org.kjh.mypracticeprojects.R
import org.kjh.mypracticeprojects.databinding.FragmentHomeBinding
import org.kjh.mypracticeprojects.model.AreaModel
import org.kjh.mypracticeprojects.model.PostModel
import org.kjh.mypracticeprojects.ui.base.BaseFragment
import org.kjh.mypracticeprojects.ui.main.MainActivity
import org.kjh.mypracticeprojects.ui.main.post.PostDetailFragment
import org.kjh.mypracticeprojects.ui.main.post.PostSmallListFragment
import org.kjh.mypracticeprojects.util.DataState
import org.kjh.mypracticeprojects.util.LinearItemDecoration


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFragment()
        initToolbarWithNavigation()
        initLocalAreaList()
    }

    private fun initFragment() {
        (activity as MainActivity).supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fr_postDetail, PostSmallListFragment.newInstance(
                    param1 = "HOME"
                )
            )
            .commit()
    }

    private fun initLocalAreaList() {
        with (binding.rvAreaList) {
            adapter = LocalAreaListAdapter(object: LocalAreaListClickEventListener {
                override fun onClickArea(area: AreaModel) {

                }
            })
            layoutManager = LinearLayoutManager(activity).apply {
                orientation = LinearLayoutManager.HORIZONTAL
                addItemDecoration(LinearItemDecoration(context))
            }
        }
    }

    private fun initToolbarWithNavigation() {
        val navController = findNavController()
        val appBarConfig = AppBarConfiguration(setOf(
            R.id.homeFragment,
            R.id.myPageFragment
        ))

        binding.tbHomeToolbar.setupWithNavController(navController, appBarConfig)
    }
}