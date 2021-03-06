package org.kjh.mypracticeprojects.ui.main.bookmark

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import org.kjh.mypracticeprojects.R
import org.kjh.mypracticeprojects.databinding.FragmentBookmarkBinding
import org.kjh.mypracticeprojects.ui.base.BaseFragment
import org.kjh.mypracticeprojects.ui.common.GridItemDecoration
import org.kjh.mypracticeprojects.ui.common.LinearVerticalItemDecoration
import org.kjh.mypracticeprojects.ui.main.MainViewModel
import org.kjh.mypracticeprojects.ui.main.post.POST_TYPE_SMALL
import org.kjh.mypracticeprojects.ui.main.post.PostListAdapter
import org.kjh.mypracticeprojects.util.DataState

@AndroidEntryPoint
class BookmarkFragment
    : BaseFragment<FragmentBookmarkBinding>(R.layout.fragment_bookmark) {

    companion object {
        const val FAILED_FETCH_BOOKMARK = "Failed Get Bookmark List"
    }

    private val mainViewModel: MainViewModel by activityViewModels()
    private val viewModel    : BookmarkViewModel by viewModels()

    private val bookmarkListAdapter by lazy {
        PostListAdapter(POST_TYPE_SMALL) {
            findNavController().navigate(
                resId = R.id.action_bookmarkFragment_to_postDetailFragment,
                args  = bundleOf("postItem" to it)
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel     = viewModel
        binding.mainViewModel = mainViewModel

        initToolbarWithNavigation()
        initRecyclerView()
        subscribeObserver()
    }

    private fun subscribeObserver() {
        mainViewModel.myUserData.observe(viewLifecycleOwner, { myData ->
            when (myData) {
                is DataState.Success -> bookmarkListAdapter.submitList(myData.data?.bookMarks)
                is DataState.Error ->
                    Toast.makeText(context, FAILED_FETCH_BOOKMARK, Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.bookmarkViewType.observe(viewLifecycleOwner, {
            updateBookMarkLayoutManager(it)
        })
    }

    private fun initRecyclerView() {
        binding.rvBookmarks.apply {
            adapter = bookmarkListAdapter
            layoutManager = GridLayoutManager(activity, 3)
            addItemDecoration(GridItemDecoration(context, 3))
        }
    }

    private fun initToolbarWithNavigation() {
        val navController = findNavController()
        val appBarConfig = AppBarConfiguration(setOf(
            R.id.homeFragment,
            R.id.bookmarkFragment,
            R.id.myPageFragment
        ))

        binding.tbBookmark.apply {
            setupWithNavController(navController, appBarConfig)
        }
    }

    private fun updateBookMarkLayoutManager(type: Int) {
        val itemDecoration = when (type) {
            POST_TYPE_SMALL -> GridItemDecoration(requireContext(), 3)
            else -> LinearVerticalItemDecoration(requireContext())
        }

        binding.rvBookmarks.apply {
            layoutManager = when (type) {
                POST_TYPE_SMALL -> GridLayoutManager(activity, 3)
                else -> LinearLayoutManager(activity)
            }

            if (itemDecorationCount == 1)
                removeItemDecorationAt(0)

            addItemDecoration(itemDecoration)
        }

        bookmarkListAdapter.changePostViewType(type)
    }
}