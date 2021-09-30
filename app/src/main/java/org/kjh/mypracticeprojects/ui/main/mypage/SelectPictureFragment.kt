package org.kjh.mypracticeprojects.ui.main.mypage

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.orhanobut.logger.Logger
import dagger.hilt.android.AndroidEntryPoint
import org.kjh.mypracticeprojects.R
import org.kjh.mypracticeprojects.databinding.FragmentSelectPictureBinding
import org.kjh.mypracticeprojects.ui.base.BaseFragment
import org.kjh.mypracticeprojects.ui.main.MainActivity
import org.kjh.mypracticeprojects.ui.main.MainViewModel
import org.kjh.mypracticeprojects.util.SpacesItemDecoration

@AndroidEntryPoint
class SelectPictureFragment :
    BaseFragment<FragmentSelectPictureBinding>(R.layout.fragment_select_picture) {

    companion object {
        const val SELECTION_ID = "my_selection_id"
    }

    private val mainViewModel: MainViewModel by activityViewModels()
    private val viewModel    : SelectPictureViewModel by viewModels()
    private lateinit var selectPictureAdapter: SelectPictureAdapter

    private val requestPermissionLauncher =
        registerForActivityResult(RequestPermission()) {
            if (it) {
                viewModel.loadLocalImages()
            } else {
                val showRationale =
                    ActivityCompat.shouldShowRequestPermissionRationale(
                        activity as MainActivity,
                        READ_EXTERNAL_STORAGE
                    )

                if (showRationale)
                    showNoAccess()
                else
                    goToSettings()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPermission()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainViewModel = mainViewModel

        initToolbarWithNavigation()
        initRecyclerView()

        // Gallery Images Observe.
        viewModel.localImages.observe(viewLifecycleOwner, { images ->
            selectPictureAdapter.submitList(images)
            mainViewModel.setUploadImgData(mainViewModel.uploadImgData.value ?: images[0])
        })
    }

    private fun initToolbarWithNavigation() {
        val navController = findNavController()

        binding.tbWriteToolbar.apply {
            inflateMenu(R.menu.menu_next)
            setupWithNavController(navController)
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menu_next -> {
                        val extras = FragmentNavigatorExtras(binding.ivSelected to getString(R.string.transition_name_upload_image))
                        val action = SelectPictureFragmentDirections
                            .actionSelectPictureFragmentToUploadContentFragment()

                        navController.navigate(action, extras)
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun initRecyclerView() {
        selectPictureAdapter = SelectPictureAdapter()
        binding.rvGallery.apply {
            adapter = selectPictureAdapter
            layoutManager = GridLayoutManager(activity, 3)
            addItemDecoration(SpacesItemDecoration(this.context))
        }

        val selectedImageTracker = SelectionTracker.Builder(
            SELECTION_ID,
            binding.rvGallery,
            StableIdKeyProvider(binding.rvGallery),
            MediaStoreImageDetailsLookup(binding.rvGallery),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(SelectionPredicates.createSelectAnything()).build()

        selectPictureAdapter.setSelectionTracker(selectedImageTracker)

        selectedImageTracker.addObserver(object: SelectionTracker.SelectionObserver<Long>() {
            override fun onItemStateChanged(key: Long, selected: Boolean) {
                super.onItemStateChanged(key, selected)

                mainViewModel.setUploadImgData(selectPictureAdapter.currentList[key.toInt()])

                if (!selected) {
                    for (num in selectedImageTracker.selection) {
                        selectPictureAdapter.notifyItemChanged(num.toInt())
                    }
                }
            }

            override fun onSelectionChanged() {
                super.onSelectionChanged()

                if (selectedImageTracker.hasSelection()) {
                    val list = selectedImageTracker.selection.map {
                        selectPictureAdapter.currentList[it.toInt()]
                    }.toMutableList()

                    mainViewModel.setMultipleImages(list)
                }
            }
        })
    }

    private fun checkPermission() {
        if (haveStoragePermission()) {
            viewModel.loadLocalImages()
        } else {
            requestPermission()
        }
    }

    private fun haveStoragePermission() =
        ContextCompat.checkSelfPermission(
            (activity as MainActivity),
            READ_EXTERNAL_STORAGE
        ) == PERMISSION_GRANTED

    private fun requestPermission() {
        requestPermissionLauncher.launch(READ_EXTERNAL_STORAGE)
    }

    private fun goToSettings() {
        Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.parse("package:org.kjh.mypracticeprojects")
        )
            .apply {
                addCategory(Intent.CATEGORY_DEFAULT)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }.also { intent ->
                startActivity(intent)
            }
    }

    private fun showNoAccess() {
        MaterialAlertDialogBuilder(activity as MainActivity)
            .setTitle(getString(R.string.permission))
            .setMessage(getString(R.string.permission_rationale_msg))
            .setPositiveButton(getString(R.string.agree)) { _, _ ->
                checkPermission()
            }
            .show()
    }
}
