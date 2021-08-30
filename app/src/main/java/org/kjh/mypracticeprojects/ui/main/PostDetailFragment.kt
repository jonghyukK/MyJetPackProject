package org.kjh.mypracticeprojects.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.transition.TransitionInflater
import com.orhanobut.logger.Logger
import org.kjh.mypracticeprojects.R
import org.kjh.mypracticeprojects.databinding.FragmentPostDetailBinding
import org.kjh.mypracticeprojects.ui.base.BaseFragment


class PostDetailFragment : BaseFragment<FragmentPostDetailBinding>(R.layout.fragment_post_detail) {

    companion object {
        const val DIALOG_X_Y_INFO = "DIALOG_X_Y_INFO"
    }

    private val args : PostDetailFragmentArgs by navArgs()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        sharedElementEnterTransition = TransitionInflater.from(context)
//            .inflateTransition(R.transition.mytransition)
//        sharedElementReturnTransition = null
//        postponeEnterTransition()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.postModel = args.postDetailFragmentArgs
        binding.fragment = this

        navController = findNavController()

        initToolbarWithNavigation()

        binding.rlLocation.setOnClickListener {
            navController.navigate(
                R.id.action_postDetailFragment_to_mapInfoFragment,
                bundleOf(DIALOG_X_Y_INFO to args.postDetailFragmentArgs)
            )
        }
    }

    private fun initToolbarWithNavigation() {
        binding.tbPostDetailToolbar.setupWithNavController(navController)
    }
}