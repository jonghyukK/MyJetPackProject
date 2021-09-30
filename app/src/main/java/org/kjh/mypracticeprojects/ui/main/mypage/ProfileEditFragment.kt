package org.kjh.mypracticeprojects.ui.main.mypage

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import com.orhanobut.logger.Logger
import dagger.hilt.android.AndroidEntryPoint
import org.kjh.mypracticeprojects.R
import org.kjh.mypracticeprojects.databinding.FragmentProfileEditBinding
import org.kjh.mypracticeprojects.ui.base.BaseFragment
import org.kjh.mypracticeprojects.ui.main.MainViewModel
import org.kjh.mypracticeprojects.util.DataState

@AndroidEntryPoint
class ProfileEditFragment
    : BaseFragment<FragmentProfileEditBinding>(R.layout.fragment_profile_edit) {

    private val viewModel    : ProfileEditViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var profileFilePath: String

    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            val uriContent = result.uriContent
            val uriFilePath = result.getUriFilePath(requireContext(), true)

            binding.ivProfileEditImg.setImageURI(uriContent)
            uriFilePath?.let {
                profileFilePath = uriFilePath
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.profileImg = arguments?.getString("profileImg")

        initToolbarWithNavigation()
        subscribeObserver()

        binding.btnProfileEdit.setOnClickListener {
            cropImage.launch(
                options {
                    setGuidelines(CropImageView.Guidelines.ON)
                    setCropShape(CropImageView.CropShape.OVAL)
                }
            )
        }
    }

    private fun subscribeObserver() {
        viewModel.resultProfileEdit.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is DataState.Loading -> binding.pbLoading.visibility = View.VISIBLE
                is DataState.Success -> {
                    mainViewModel.updateMyUserData(dataState)
                    binding.pbLoading.visibility = View.GONE
                    findNavController().popBackStack()
                }
                is DataState.Error -> {
                    Toast.makeText(requireContext(), dataState.exception.toString(), Toast.LENGTH_SHORT).show()
                    binding.pbLoading.visibility = View.GONE
                }
            }
        })
    }

    private fun initToolbarWithNavigation() {
        binding.tbProfileEdit.apply {
            setupWithNavController(findNavController())
            inflateMenu(R.menu.menu_profile_edit)
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menu_apply -> {
                        viewModel.updateProfile(profileFilePath)
                        true
                    }
                    else -> false
                }
            }
        }
    }
}