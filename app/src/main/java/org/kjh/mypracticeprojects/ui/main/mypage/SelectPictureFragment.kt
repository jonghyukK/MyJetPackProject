package org.kjh.mypracticeprojects.ui.main.mypage

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import org.kjh.mypracticeprojects.R
import org.kjh.mypracticeprojects.databinding.FragmentSelectPictureBinding
import org.kjh.mypracticeprojects.ui.base.BaseFragment
import org.kjh.mypracticeprojects.ui.main.MainActivity
import org.kjh.mypracticeprojects.ui.main.MainViewModel
import org.kjh.mypracticeprojects.util.SpacesItemDecoration
import java.util.*

@AndroidEntryPoint
class SelectPictureFragment :
    BaseFragment<FragmentSelectPictureBinding>(R.layout.fragment_select_picture) {

    private val mainViewModel: MainViewModel by activityViewModels()
    private val viewModel    : SelectPictureViewModel by viewModels()

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
        binding.viewModel = viewModel

        initToolbarWithNavigation()

        val galleryAdapter = GalleryAdapter { selectedImg ->
            mainViewModel.setUploadImgData(selectedImg)
        }

        binding.rvGallery.apply {
            layoutManager = GridLayoutManager(activity, 3)
            adapter = galleryAdapter
            addItemDecoration(SpacesItemDecoration(this.context))
        }

        // Gallery Images Observe.
        viewModel.localImages.observe(viewLifecycleOwner, { images ->
            galleryAdapter.submitList(images)
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
            .setTitle("권한")
            .setMessage("해당 권한을 허용하지 않으면 글쓰기 기능을 이용하실 수 없습니다.")
            .setPositiveButton("허용") { _, _ ->
                checkPermission()
            }
            .show()
    }


    private inner class GalleryAdapter(val onClick: (MediaStoreImage) -> Unit):
            ListAdapter<MediaStoreImage, ImageViewHolder>(MediaStoreImage.DiffCallback) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_gallery_image, parent, false)
            return ImageViewHolder(view, onClick)
        }

        override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
            val mediaStoreImage = getItem(position)
            holder.rootView.tag = mediaStoreImage

            Glide.with(holder.imageView)
                .load(mediaStoreImage.contentUri)
                .thumbnail(0.33f)
                .centerCrop()
                .into(holder.imageView)
        }
    }
}

private class ImageViewHolder(view: View, onClick: (MediaStoreImage) -> Unit) :
    RecyclerView.ViewHolder(view) {
    val rootView = view
    val imageView: ImageView = view.findViewById(R.id.image)

    init {
        imageView.setOnClickListener {
            val image = rootView.tag as? MediaStoreImage ?: return@setOnClickListener
            onClick(image)
        }
    }
}