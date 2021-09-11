package org.kjh.mypracticeprojects.ui.main.post

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.children
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.orhanobut.logger.Logger
import dagger.hilt.android.AndroidEntryPoint
import org.kjh.mypracticeprojects.R
import org.kjh.mypracticeprojects.databinding.FragmentPostListByCityBinding
import org.kjh.mypracticeprojects.model.AreaModel
import org.kjh.mypracticeprojects.model.PostModel
import org.kjh.mypracticeprojects.navigate
import org.kjh.mypracticeprojects.replaceFragment
import org.kjh.mypracticeprojects.ui.base.BaseFragment
import org.kjh.mypracticeprojects.ui.main.*
import org.kjh.mypracticeprojects.ui.main.PostListClickEventListener
import org.kjh.mypracticeprojects.ui.main.home.LocalAreaListAdapter
import org.kjh.mypracticeprojects.ui.main.home.LocalAreaListClickEventListener
import org.kjh.mypracticeprojects.util.DataState
import org.kjh.mypracticeprojects.util.LinearItemDecoration
import org.kjh.mypracticeprojects.util.LinearVerticalItemDecoration
import org.kjh.mypracticeprojects.util.SpacesItemDecoration

@AndroidEntryPoint
class PostListByCityFragment
    : BaseFragment<FragmentPostListByCityBinding>(R.layout.fragment_post_list_by_city) {

    private val viewModel: PostListByCityViewModel by viewModels()
    private lateinit var cityData: AreaModel
    private lateinit var postListByCityAdapter: PostListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cityData = it.get("areaModel") as AreaModel
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.areaModel = cityData
        binding.viewModel = viewModel

        if (viewModel.postListByCity.value == null) {
            viewModel.getPostListByCity(cityData.areaName)
        }

        viewModel.postListByCity.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    postListByCityAdapter.submitList(dataState.data)
                }
            }
        })

        viewModel.postListViewType.observe(viewLifecycleOwner, {
            setPostListLayoutManager(it)
            postListByCityAdapter.changePostViewType(it)
        })

        initPostListByCity()
        initCityItemList()
        initToolbarWithNavigation()
    }

    private fun setPostListLayoutManager(type: Int) {
        val itemDecoration = when (type) {
            POST_TYPE_SMALL -> SpacesItemDecoration(this.requireContext())
            else -> LinearVerticalItemDecoration(this.requireContext())
        }

        with (binding.rvPostListByCity) {
            layoutManager = when (type) {
                POST_TYPE_SMALL -> GridLayoutManager(activity, 3)
                else -> LinearLayoutManager(activity)
            }

            if (itemDecorationCount == 1)
                removeItemDecorationAt(0)

            addItemDecoration(itemDecoration)
        }
    }

    private fun initPostListByCity() {
        postListByCityAdapter = PostListAdapter(object: PostListClickEventListener {
            override fun onClickPost(item: PostModel) {
                navigate(
                    action = R.id.action_postListByCityFragment_to_postDetailFragment,
                    bundle = bundleOf("postItem" to item)
                )
            }
        }, POST_TYPE_LARGE)

        with (binding.rvPostListByCity) {
            adapter = postListByCityAdapter
        }
    }

    private fun initCityItemList() {
        with (binding.rvCityList) {
            adapter = LocalAreaListAdapter(object: LocalAreaListClickEventListener {
                override fun onClickArea(area: AreaModel) {
                    viewModel.getPostListByCity(area.areaName)
                    binding.areaModel = area
                    cityData = area
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
        binding.tbPostListByCity.setupWithNavController(navController)
    }
}