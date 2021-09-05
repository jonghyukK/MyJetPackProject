package org.kjh.mypracticeprojects.ui.main.post

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import org.kjh.mypracticeprojects.R
import org.kjh.mypracticeprojects.databinding.FragmentPostListBinding
import org.kjh.mypracticeprojects.model.PostModel
import org.kjh.mypracticeprojects.ui.base.BaseFragment
import org.kjh.mypracticeprojects.ui.main.MainViewModel
import org.kjh.mypracticeprojects.ui.main.mypage.MyPageFragmentDirections
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
    BaseFragment<FragmentPostListBinding>(R.layout.fragment_post_list),
    PostListClickEventListener {

    private val mainViewModel: MainViewModel by activityViewModels()
    private val args: PostListFragmentArgs by navArgs()
    private lateinit var cityKey: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        cityKey = args.postListFragmentArgs

        initToolbarWithNavigation()
        val postListAdapter = PostListAdapter(this)

        binding.rvMyImages.apply {
            layoutManager = GridLayoutManager(activity, 3)
            adapter = postListAdapter
            addItemDecoration(SpacesItemDecoration(this.context))
        }

        mainViewModel.myUserData.observe(viewLifecycleOwner, { myData ->
            myData.posts[cityKey]?.let {
                postListAdapter.submitList(myData.posts[cityKey])
            }
        })
    }

    private fun initToolbarWithNavigation() {
        with(binding.tbPostListToolbar) {
            setupWithNavController(findNavController())
            title = cityKey
        }
    }

    override fun onClickPost(postItem: PostModel) {
        val postLists = mainViewModel.myUserData.value!!.posts[cityKey]!!

        val bundle = bundleOf(
            "postList" to postLists,
            "postId" to postItem.postId
        )
        findNavController().navigate(R.id.action_postListFragment_to_postDetailFragment, bundle)
    }
}

