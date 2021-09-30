package org.kjh.mypracticeprojects.ui.main.post

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import org.kjh.mypracticeprojects.R
import org.kjh.mypracticeprojects.databinding.FragmentPostListByCityBinding
import org.kjh.mypracticeprojects.model.CityModel
import org.kjh.mypracticeprojects.model.PostModel
import org.kjh.mypracticeprojects.navigate
import org.kjh.mypracticeprojects.ui.base.BaseFragment
import org.kjh.mypracticeprojects.ui.main.*
import org.kjh.mypracticeprojects.ui.main.home.LocalAreaListAdapter
import org.kjh.mypracticeprojects.ui.main.home.LocalAreaListClickEventListener
import org.kjh.mypracticeprojects.util.DataState
import org.kjh.mypracticeprojects.ui.common.LinearItemDecoration
import org.kjh.mypracticeprojects.ui.common.LinearVerticalItemDecoration
import org.kjh.mypracticeprojects.ui.common.GridItemDecoration

@AndroidEntryPoint
class PostListByCityFragment
    : BaseFragment<FragmentPostListByCityBinding>(R.layout.fragment_post_list_by_city) {

    private val viewModel: PostListByCityViewModel by viewModels()
    private lateinit var cityData: CityModel
    private lateinit var postListByCityAdapter: PostListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cityData = it.get("cityModel") as CityModel
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cityModel = cityData
        binding.viewModel = viewModel

        if (viewModel.postListByCity.value == null) {
            viewModel.getPostListByCity(cityData.cityName)
        }

        initPostListByCity()
        initCityItemList()
        initToolbarWithNavigation()
        subscribeObserver()
    }

    private fun subscribeObserver() {
        viewModel.postListByCity.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is DataState.Success -> postListByCityAdapter.submitList(dataState.data)
            }
        })

        viewModel.postListViewType.observe(viewLifecycleOwner, {
            updatePostListLayoutManager(it)
        })
    }

    private fun updatePostListLayoutManager(type: Int) {
        val itemDecoration = when (type) {
            POST_TYPE_SMALL -> GridItemDecoration(requireContext(), 3)
            else -> LinearVerticalItemDecoration(requireContext())
        }

        binding.rvPostListByCity.apply {
            layoutManager = when (type) {
                POST_TYPE_SMALL -> GridLayoutManager(activity, 3)
                else -> LinearLayoutManager(activity)
            }

            if (itemDecorationCount == 1)
                removeItemDecorationAt(0)

            addItemDecoration(itemDecoration)
        }

        postListByCityAdapter.changePostViewType(type)
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

        binding.rvPostListByCity.apply {
            adapter       = postListByCityAdapter
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(LinearVerticalItemDecoration(requireContext()))
        }
    }

    private fun initCityItemList() {
        binding.rvCityList.apply {
            adapter = LocalAreaListAdapter(object: LocalAreaListClickEventListener {
                override fun onClickCity(city: CityModel) {
                    viewModel.getPostListByCity(city.cityName)
                    binding.cityModel = city
                    cityData = city
                }
            })
            layoutManager = LinearLayoutManager(activity).apply {
                orientation = LinearLayoutManager.HORIZONTAL
                addItemDecoration(LinearItemDecoration(context))
            }
        }
    }

    private fun initToolbarWithNavigation() {
        binding.tbPostListByCity.setupWithNavController(findNavController())
    }
}