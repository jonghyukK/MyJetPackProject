package org.kjh.mypracticeprojects.ui.main.home

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.orhanobut.logger.Logger
import dagger.hilt.android.AndroidEntryPoint
import org.kjh.mypracticeprojects.R
import org.kjh.mypracticeprojects.databinding.FragmentHomeBinding
import org.kjh.mypracticeprojects.model.AreaModel
import org.kjh.mypracticeprojects.model.PostModel
import org.kjh.mypracticeprojects.navigate
import org.kjh.mypracticeprojects.ui.base.BaseFragment
import org.kjh.mypracticeprojects.ui.main.*
import org.kjh.mypracticeprojects.util.DataState
import org.kjh.mypracticeprojects.util.LinearItemDecoration
import org.kjh.mypracticeprojects.util.LinearVerticalItemDecoration


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var recentPostListAdapter: PostListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (viewModel.recentPostList.value == null) {
            viewModel.getRecentPostList()
        }

        viewModel.recentPostList.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    recentPostListAdapter.submitList(dataState.data)
                }
            }
        })

        initSwipeRefreshLayout()
        initRecentPostList()
        initCityItemList()
        initToolbarWithNavigation()
    }

    private fun initSwipeRefreshLayout() {
        binding.ablHome.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            binding.srlSwipeLayout.isEnabled = verticalOffset == 0
        })

        binding.srlSwipeLayout.setOnRefreshListener {
            viewModel.getRecentPostList()
            binding.srlSwipeLayout.isRefreshing = false
        }

    }

    private fun initRecentPostList() {
        recentPostListAdapter = PostListAdapter(object: PostListClickEventListener {
            override fun onClickPost(item: PostModel) {
                navigate(
                    action = R.id.action_homeFragment_to_postDetailFragment,
                    bundle = bundleOf("postItem" to item)
                )
            }
        }, POST_TYPE_MEDIUM)

        with (binding.rvRecentPostList) {
            adapter = recentPostListAdapter
            layoutManager = LinearLayoutManager(context).apply {
                orientation = LinearLayoutManager.VERTICAL
                addItemDecoration(LinearVerticalItemDecoration(context))
            }
        }
    }

    private fun initCityItemList() {
        with (binding.rvAreaList) {
            adapter = LocalAreaListAdapter(object: LocalAreaListClickEventListener {
                override fun onClickArea(area: AreaModel) {
                    navigate(
                        action = R.id.action_homeFragment_to_postListByCityFragment,
                        bundle = bundleOf("areaModel" to area)
                    )
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