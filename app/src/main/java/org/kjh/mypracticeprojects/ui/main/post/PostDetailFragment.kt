package org.kjh.mypracticeprojects.ui.main.post

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import org.kjh.mypracticeprojects.MyApplication
import org.kjh.mypracticeprojects.PREF_KEY_LOGIN_ID
import org.kjh.mypracticeprojects.R
import org.kjh.mypracticeprojects.databinding.FragmentPostDetailBinding
import org.kjh.mypracticeprojects.model.PostModel
import org.kjh.mypracticeprojects.navigate
import org.kjh.mypracticeprojects.ui.base.BaseFragment
import org.kjh.mypracticeprojects.ui.main.MainActivity
import org.kjh.mypracticeprojects.ui.main.MainViewModel

@AndroidEntryPoint
class PostDetailFragment :
    BaseFragment<FragmentPostDetailBinding>(R.layout.fragment_post_detail),
    PostDetailClickEventListener {

    companion object {
        const val LOCATION_INFO = "LOCATION_INFO"
    }

    private val viewModelDeprecated: PostDetailViewModel_deprecated by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    private lateinit var navController: NavController
    private lateinit var postList: List<PostModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            postList = it.get("postList") as List<PostModel>
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            loadView()


//        viewModel.deleteResult.observe(viewLifecycleOwner, { dataState ->
//            when (dataState) {
//                is DataState.Success -> {
//                    dataState.data?.let {
//                        mainViewModel.updateMyUserData(it)
//
//                        if (it.posts[postData.cityCategory].isNullOrEmpty())
//                            navController.navigate(R.id.action_postDetailFragment_to_myPageFragment)
//                        else
//                            navController.popBackStack()
//                    }
//                }
//                is DataState.Error ->
//                    Toast.makeText(context, "게시물 삭제가 실패하였습니다.", Toast.LENGTH_LONG).show()
//            }
//        })
    }

    private fun loadView() {
        initToolbarWithNavigation()
        initRecyclerView()
    }

    private fun initToolbarWithNavigation() {
        navController = findNavController()
        binding.tbPostDetailToolbar.setupWithNavController(navController)
    }

    private fun initRecyclerView() {
        val thisAdapter = PostDetailListAdapter(this@PostDetailFragment).apply {
            submitList(postList)
        }

        with (binding.rvPostDetailList) {
            adapter = thisAdapter
            layoutManager = LinearLayoutManager(context).apply {
                orientation = LinearLayoutManager.VERTICAL
                scrollToPositionWithOffset(
                    postList.indexOf(postList.find { it.postId == requireArguments().get("postId") })
                    , 0)
            }
        }
    }

    override fun onClickMap(item: PostModel) {
        navigate(
            R.id.action_postDetailFragment_to_mapInfoFragment,
            bundleOf(LOCATION_INFO to item)
        )
    }

    override fun onClickMore(postId: Int) {
        val btmSheet = PostBottomSheetFragment(object: PostBottomSheetEventListener {
            override fun onClickDeletePost() {
                val myEmail = MyApplication.prefs.getPref(PREF_KEY_LOGIN_ID, "")
                viewModelDeprecated.deletePost(
                    postId = postId,
                    email  = myEmail
                )
            }
        })
        btmSheet.show((activity as MainActivity).supportFragmentManager, "tag")
    }
}

