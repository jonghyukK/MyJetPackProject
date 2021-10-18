package org.kjh.mypracticeprojects.ui.main.home

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
import org.kjh.mypracticeprojects.R
import org.kjh.mypracticeprojects.databinding.FragmentHomeBinding
import org.kjh.mypracticeprojects.ui.base.BaseFragment
import org.kjh.mypracticeprojects.ui.common.LinearItemDecoration
import org.kjh.mypracticeprojects.ui.common.LinearVerticalItemDecoration
import org.kjh.mypracticeprojects.ui.main.post.POST_TYPE_MEDIUM
import org.kjh.mypracticeprojects.ui.main.post.PostListAdapter
import org.kjh.mypracticeprojects.util.DataState


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()

    private val recentPostListAdapter by lazy {
        PostListAdapter(POST_TYPE_MEDIUM) {
            findNavController().navigate(
                resId = R.id.action_homeFragment_to_postDetailFragment,
                args = bundleOf("postItem" to it)
            )
        }
    }

    private val localAreaListAdapter by lazy {
        LocalAreaListAdapter {
            findNavController().navigate(
                resId = R.id.action_homeFragment_to_postListByCityFragment,
                args  = bundleOf("cityModel" to it)
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSwipeRefreshLayout()
        initRecentPostList()
        initCityItemList()
        initToolbarWithNavigation()
        subscribeObserve()
    }

    private fun subscribeObserve() {
        viewModel.recentPostList.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    recentPostListAdapter.submitList(dataState.data)
                }
            }
        })
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
        binding.rvRecentPostList.apply {
            adapter = recentPostListAdapter
            layoutManager = LinearLayoutManager(context).apply {
                orientation = LinearLayoutManager.VERTICAL
                addItemDecoration(LinearVerticalItemDecoration(context))
            }
        }
    }

    private fun initCityItemList() {
        binding.rvAreaList.apply {
            adapter = localAreaListAdapter
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