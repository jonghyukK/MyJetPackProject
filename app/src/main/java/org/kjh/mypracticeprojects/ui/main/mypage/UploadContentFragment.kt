package org.kjh.mypracticeprojects.ui.main.mypage

import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.transition.TransitionInflater
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import dagger.hilt.android.AndroidEntryPoint
import org.kjh.mypracticeprojects.R
import org.kjh.mypracticeprojects.databinding.FragmentUploadContentBinding
import org.kjh.mypracticeprojects.ui.base.BaseFragment
import org.kjh.mypracticeprojects.ui.main.MainViewModel
import org.kjh.mypracticeprojects.util.DataState
import org.kjh.mypracticeprojects.util.GlideApp

@AndroidEntryPoint
class UploadContentFragment :
    BaseFragment<FragmentUploadContentBinding>(R.layout.fragment_upload_content) {

    private val viewModel: UploadContentViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainViewModel = mainViewModel
        binding.viewModel = viewModel

        initToolbarWithNavigation()

        setSharedElementTransitionOnEnter()
        postponeEnterTransition()

        mainViewModel.uploadImgData.observe(viewLifecycleOwner, { selectedImgItem ->
            binding.ivTempImg.apply {
                startEnterTransitionAfterLoadingImage(selectedImgItem!!.contentUri)
            }
        })

        binding.rlAddLocation.setOnClickListener {
            findNavController().navigate(R.id.action_uploadContentFragment_to_mapFragment)
        }

        viewModel.uploadResult.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    mainViewModel.updateMyUserData(dataState.data!!)
                    findNavController().navigate(R.id.action_uploadContentFragment_to_myPageFragment)
                }
                is DataState.Error -> Toast.makeText(context, "업로드가 실패하였습니다.", Toast.LENGTH_LONG).show()
                is DataState.Loading -> binding.pbLoading.visibility = View.VISIBLE
            }
        })
    }

    private fun setSharedElementTransitionOnEnter() {
        sharedElementEnterTransition = TransitionInflater.from(context)
            .inflateTransition(R.transition.transition)
        sharedElementReturnTransition = null
    }

    private fun startEnterTransitionAfterLoadingImage(imageUri: Uri){
        GlideApp.with(this)
            .load(imageUri)
            .dontAnimate()
            .centerCrop()
            .listener(object: RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }
            })
            .into(binding.ivTempImg)
    }

    private fun initToolbarWithNavigation() {
        val navController = findNavController()

        binding.tbUploadToolbar.apply {
            inflateMenu(R.menu.menu_upload)
            setupWithNavController(navController)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_upload -> {
                        viewModel.uploadContent(
                            mainViewModel.multipleImages.value!!,
                            mainViewModel.uploadLocationData.value!!
                        )
                        true
                    }
                    else -> false
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mainViewModel.setUploadLocationData(null)
    }
}