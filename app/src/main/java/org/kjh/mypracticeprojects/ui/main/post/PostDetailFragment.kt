package org.kjh.mypracticeprojects.ui.main.post

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import org.kjh.mypracticeprojects.R
import org.kjh.mypracticeprojects.databinding.FragmentPostDetailBinding
import org.kjh.mypracticeprojects.model.PostModel
import org.kjh.mypracticeprojects.ui.base.BaseFragment

class PostDetailFragment
    : BaseFragment<FragmentPostDetailBinding>(R.layout.fragment_post_detail) {

    companion object {
        const val DIALOG_X_Y_INFO = "DIALOG_X_Y_INFO"
    }

    private lateinit var navController: NavController
    private lateinit var postData: PostModel
    private val args : PostDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with (args.postDetailFragmentArgs) {
            postData = this
            binding.postModel = this
        }

        initToolbarWithNavigation()
        initViewPager()

        binding.rlLocation.setOnClickListener {
            navController.navigate(
                R.id.action_postDetailFragment_to_mapInfoFragment,
                bundleOf(DIALOG_X_Y_INFO to args.postDetailFragmentArgs)
            )
        }
    }

    private fun initToolbarWithNavigation() {
        navController = findNavController()
        binding.tbPostDetailToolbar.setupWithNavController(navController)
    }

    private fun initViewPager() {
        binding.vpPostDetail.apply {
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
}

