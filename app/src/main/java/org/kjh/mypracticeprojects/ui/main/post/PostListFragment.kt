package org.kjh.mypracticeprojects.ui.main.post

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
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
    private lateinit var cityKey: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        cityKey = arguments?.getString("City") ?: "전체"

        val postListAdapter = PostListAdapter(this)

        binding.rvMyImages.apply {
            layoutManager = GridLayoutManager(activity, 3)
            adapter = postListAdapter
            addItemDecoration(SpacesItemDecoration(this.context))
        }

        mainViewModel.myUserData.observe(viewLifecycleOwner, { myData ->
            myData.posts[cityKey]?.let {
                postListAdapter.submitList(myData.posts[cityKey]?.reversed())
                binding.tvEmptyPosts.visibility = View.GONE
            }
        })
    }

    override fun onClickPost(postItem: PostModel) {
        val action = MyPageFragmentDirections
            .actionMyPageFragmentToPostDetailFragment(postDetailFragmentArgs = postItem)
        findNavController().navigate(action)
    }
}

