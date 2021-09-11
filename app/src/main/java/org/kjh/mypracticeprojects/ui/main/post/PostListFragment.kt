package org.kjh.mypracticeprojects.ui.main.post

import android.os.Bundle
import android.view.View
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
import org.kjh.mypracticeprojects.ui.main.MainViewModel
import org.kjh.mypracticeprojects.ui.main.POST_TYPE_SMALL
import org.kjh.mypracticeprojects.ui.main.PostListAdapter
import org.kjh.mypracticeprojects.ui.main.PostListClickEventListener
import org.kjh.mypracticeprojects.util.LinearVerticalItemDecoration
import org.kjh.mypracticeprojects.util.SpacesItemDecoration

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

    private val mainViewModel: MainViewModel by activityViewModels()
    private val viewModel: PostListViewModel by viewModels()
    private val args: PostListFragmentArgs by navArgs()
    private lateinit var postListAdapter: PostListAdapter
    private lateinit var cityKey: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        cityKey = args.postListFragmentArgs
        binding.viewModel = viewModel

        mainViewModel.myUserData.observe(viewLifecycleOwner, { myData ->
            myData!!.posts[cityKey]?.let {
                postListAdapter.submitList(myData.posts[cityKey])
            }
        })

        viewModel.postListViewType.observe(viewLifecycleOwner, {
            setPostListLayoutManager(it)
            postListAdapter.changePostViewType(it)
        })

        initToolbarWithNavigation()
        initCityItemList()
    }

    private fun setPostListLayoutManager(type: Int) {
        val itemDecoration = when (type) {
            POST_TYPE_SMALL -> SpacesItemDecoration(this.requireContext())
            else -> LinearVerticalItemDecoration(this.requireContext())
        }

        with (binding.rvMyImages) {
            layoutManager = when (type) {
                POST_TYPE_SMALL -> GridLayoutManager(activity, 3)
                else -> LinearLayoutManager(activity)
            }

            if (itemDecorationCount == 1)
                removeItemDecorationAt(0)

            addItemDecoration(itemDecoration)
        }
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

        with (binding.rvMyImages) {
            adapter = postListAdapter
        }
    }

    private fun initToolbarWithNavigation() {
        with(binding.tbPostListToolbar) {
            setupWithNavController(findNavController())
            title = cityKey
        }
    }
}

