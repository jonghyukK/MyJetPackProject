package org.kjh.mypracticeprojects.ui.main.mypage

import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.transition.TransitionInflater
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.orhanobut.logger.Logger
import dagger.hilt.android.AndroidEntryPoint
import org.kjh.mypracticeprojects.R
import org.kjh.mypracticeprojects.databinding.FragmentUploadContentBinding
import org.kjh.mypracticeprojects.model.LocationItem
import org.kjh.mypracticeprojects.ui.MainViewModel
import org.kjh.mypracticeprojects.ui.base.BaseFragment
import org.kjh.mypracticeprojects.util.GlideApp

@AndroidEntryPoint
class UploadContentFragment : BaseFragment<FragmentUploadContentBinding>(R.layout.fragment_upload_content) {

    private val viewModel: UploadContentViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        initToolbarWithNavigation()

        setSharedElementTransitionOnEnter()
        postponeEnterTransition()

        mainViewModel.uploadImgData.observe(viewLifecycleOwner, { selectedImgItem ->
            binding.ivTempImg.apply {
                startEnterTransitionAfterLoadingImage(selectedImgItem!!.contentUri)
            }
        })

        mainViewModel.uploadLocationData.observe(viewLifecycleOwner, { selectedLocateItem ->
            binding.tvLocation.text = selectedLocateItem?.road_address_name ?: "위치 추가"
        })

        binding.rlAddLocation.setOnClickListener {
            findNavController().navigate(R.id.action_uploadContentFragment_to_mapFragment)
        }
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
                            mainViewModel.uploadImgData.value!!,
                            mainViewModel.uploadLocationData.value!!
                        )
//                        findNavController().navigate(R.id.action_uploadContentFragment_to_myPageFragment)
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