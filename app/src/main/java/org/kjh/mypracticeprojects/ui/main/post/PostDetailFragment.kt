package org.kjh.mypracticeprojects.ui.main.post

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.orhanobut.logger.Logger
import dagger.hilt.android.AndroidEntryPoint
import org.kjh.mypracticeprojects.MyApplication
import org.kjh.mypracticeprojects.PREF_KEY_LOGIN_ID
import org.kjh.mypracticeprojects.R
import org.kjh.mypracticeprojects.databinding.FragmentPostDetailBinding
import org.kjh.mypracticeprojects.model.PostModel
import org.kjh.mypracticeprojects.ui.base.BaseFragment
import org.kjh.mypracticeprojects.ui.main.MainActivity
import org.kjh.mypracticeprojects.ui.main.MainViewModel
import org.kjh.mypracticeprojects.util.DataState

@AndroidEntryPoint
class PostDetailFragment
    : BaseFragment<FragmentPostDetailBinding>(R.layout.fragment_post_detail),
PostBottomSheetEventListener {

    companion object {
        const val LOCATION_INFO = "LOCATION_INFO"
    }

    private val viewModel: PostDetailViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private val args : PostDetailFragmentArgs by navArgs()

    private lateinit var navController: NavController
    private lateinit var postData: PostModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.postDetailFragmentArgs.run {
            postData = this
            binding.postModel = this
            binding.viewModel = viewModel
        }

        initToolbarWithNavigation()
        initViewPager()

        viewModel.deleteResult.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    dataState.data?.let {
                        mainViewModel.updateMyUserData(it)
                        navController.popBackStack()
                    }
                }
                is DataState.Error ->
                    Toast.makeText(context, "게시물 삭제가 실패하였습니다.", Toast.LENGTH_LONG).show()
            }
        })

        binding.rlLocation.setOnClickListener {
            navController.navigate(
                R.id.action_postDetailFragment_to_mapInfoFragment,
                bundleOf(LOCATION_INFO to args.postDetailFragmentArgs)
            )
        }

        binding.btnMore.setOnClickListener {
            val btmSheet = PostBottomSheetFragment(this)
            btmSheet.show((activity as MainActivity).supportFragmentManager, "tag")
        }
    }

    private fun initToolbarWithNavigation() {
        navController = findNavController()
        binding.tbPostDetailToolbar.setupWithNavController(navController)
    }

    private fun initViewPager() {
        with (binding.vpPostDetail) {
            adapter = PostDetailImageAdapter().apply {
                setImageList(args.postDetailFragmentArgs.imageUrl)
            }
            orientation = ViewPager2.ORIENTATION_HORIZONTAL

            registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(pos: Int) {
                    binding.tvImageCount.text =
                        String.format("%d/%d", pos + 1, postData.imageUrl.size)
                }
            })

            setPageTransformer { page, position ->
                if (position <= 0f) {
                    page.translationX = 0f
                    page.scaleX = 1f
                    page.scaleY = 1f
                } else if (position <= 1f) {
                    val scaleFactor = 0.75f + (1 - 0.75f) * (1 - Math.abs(position))
                    page.alpha = 1 - position
                    page.pivotY = 0.5f * page.height
                    page.translationX = page.width * - position
                    page.scaleX = scaleFactor
                    page.scaleY = scaleFactor
                }
            }
        }
    }

    override fun onClickDeletePost() {
        val myEmail = MyApplication.prefs.getPref(PREF_KEY_LOGIN_ID, "")
        viewModel.deletePost(
            postId = postData.postId,
            email  = myEmail
        )
    }
}

