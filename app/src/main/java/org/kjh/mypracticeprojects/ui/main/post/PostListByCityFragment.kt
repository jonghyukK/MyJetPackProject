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
import org.kjh.mypracticeprojects.ui.base.BaseFragment
import org.kjh.mypracticeprojects.ui.common.GridItemDecoration
import org.kjh.mypracticeprojects.ui.common.LinearItemDecoration
import org.kjh.mypracticeprojects.ui.common.LinearVerticalItemDecoration
import org.kjh.mypracticeprojects.ui.main.home.LocalAreaListAdapter
import org.kjh.mypracticeprojects.util.DataState

@AndroidEntryPoint
class PostListByCityFragment
    : BaseFragment<FragmentPostListByCityBinding>(R.layout.fragment_post_list_by_city) {

    private lateinit var cityData: CityModel
    private val viewModel: PostListByCityViewModel by viewModels()

    private val postListByCityAdapter by lazy {
        PostListAdapter(POST_TYPE_LARGE) {
            findNavController().navigate(
                resId = R.id.action_postListByCityFragment_to_postDetailFragment,
                args  = bundleOf("postItem" to it)
            )
        }
    }

    private val localAreaListAdapter by lazy {
        LocalAreaListAdapter {
            viewModel.getPostListByCity(it.cityName)
            cityData = it
        }
    }

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
                is DataState.Success -> {
                    postListByCityAdapter.submitList(dataState.data)
                    binding.cityModel = cityData
                }
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
        binding.rvPostListByCity.apply {
            adapter       = postListByCityAdapter
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(LinearVerticalItemDecoration(requireContext()))
        }
    }

    private fun initCityItemList() {
        binding.rvCityList.apply {
            adapter = localAreaListAdapter
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