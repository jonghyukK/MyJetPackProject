package org.kjh.mypracticeprojects.ui.main.post

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import org.kjh.mypracticeprojects.R
import org.kjh.mypracticeprojects.databinding.FragmentPostListBinding
import org.kjh.mypracticeprojects.model.PostModel
import org.kjh.mypracticeprojects.navigate
import org.kjh.mypracticeprojects.ui.base.BaseFragment
import org.kjh.mypracticeprojects.ui.common.GridItemDecoration
import org.kjh.mypracticeprojects.ui.common.LinearVerticalItemDecoration
import org.kjh.mypracticeprojects.ui.main.MainViewModel
import org.kjh.mypracticeprojects.util.DataState

/**
 * MyPracticeProjects
 * Class: AreaImageFragment
 * Created by mac on 2021/08/16.
 *
 * Description:
 */
@AndroidEntryPoint
class PostListFragment:
    BaseFragment<FragmentPostListBinding>(R.layout.fragment_post_list) {

    companion object {
        const val FAILED_FETCH_POST_LIST = "Failed Fetch Post List."
    }

    private val mainViewModel: MainViewModel by activityViewModels()
    private val viewModel    : PostListViewModel by viewModels()
    private val args         : PostListFragmentArgs by navArgs()

    private lateinit var postListAdapter: PostListAdapter
    private lateinit var cityKey: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        cityKey               = args.postListFragmentArgs
        binding.viewModel     = viewModel
        binding.mainViewModel = mainViewModel

        initToolbarWithNavigation()
        initCityItemList()
        subscribeObserver()
    }

    private fun subscribeObserver() {
        mainViewModel.myUserData.observe(viewLifecycleOwner, { myData ->
            when (myData) {
                is DataState.Success -> {
                    myData.data?.let {
                        it.posts[cityKey].let {
                            postListAdapter.submitList(myData.successData()!!.posts[cityKey])
                        }
                    }
                }
                is DataState.Error ->
                    Toast.makeText(requireContext(), FAILED_FETCH_POST_LIST, Toast.LENGTH_SHORT).show()
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

        binding.rvMyImages.apply {
            layoutManager = when (type) {
                POST_TYPE_SMALL -> GridLayoutManager(activity, 3)
                else -> LinearLayoutManager(activity)
            }

            if (itemDecorationCount == 1)
                removeItemDecorationAt(0)

            addItemDecoration(itemDecoration)
        }

        postListAdapter.changePostViewType(type)
    }

    private fun initCityItemList() {
        postListAdapter = PostListAdapter(object: PostListClickEventListener {
            override fun onClickPost(item: PostModel) {
                navigate(
                    action = R.id.action_postListFragment_to_postDetailFragment,
                    bundle = bundleOf("postItem" to item)
                )
            }
        }, POST_TYPE_SMALL)

        binding.rvMyImages.apply {
            adapter       = postListAdapter
            layoutManager = GridLayoutManager(activity, 3)
            addItemDecoration(GridItemDecoration(context, 3))
        }
    }

    private fun initToolbarWithNavigation() {
        with(binding.tbPostListToolbar) {
            setupWithNavController(findNavController())
            title = cityKey
        }
    }
}

