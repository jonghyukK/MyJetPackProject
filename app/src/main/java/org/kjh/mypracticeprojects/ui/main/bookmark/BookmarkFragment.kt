package org.kjh.mypracticeprojects.ui.main.bookmark

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import org.kjh.mypracticeprojects.model.PostModel
import org.kjh.mypracticeprojects.navigate
import org.kjh.mypracticeprojects.ui.base.BaseFragment
import org.kjh.mypracticeprojects.ui.main.MainViewModel
import org.kjh.mypracticeprojects.ui.main.POST_TYPE_SMALL
import org.kjh.mypracticeprojects.ui.main.PostListAdapter
import org.kjh.mypracticeprojects.ui.main.PostListClickEventListener
import org.kjh.mypracticeprojects.util.DataState
import org.kjh.mypracticeprojects.util.LinearVerticalItemDecoration
import org.kjh.mypracticeprojects.util.SpacesItemDecoration

@AndroidEntryPoint
class BookmarkFragment
    : BaseFragment<FragmentBookmarkBinding>(R.layout.fragment_bookmark) {

    companion object {
        const val FAILED_FETCH_BOOKMARK = "Failed Get Bookmark List"
    }

    private val mainViewModel: MainViewModel by activityViewModels()
    private val viewModel    : BookmarkViewModel by viewModels()

    private lateinit var bookmarkListAdapter: PostListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel     = viewModel
        binding.mainViewModel = mainViewModel

        initToolbarWithNavigation()
        initRecyclerView()

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
        bookmarkListAdapter = PostListAdapter(object: PostListClickEventListener {
            override fun onClickPost(item: PostModel) {
                navigate(
                    action = R.id.action_bookmarkFragment_to_postDetailFragment,
                    bundle = bundleOf("postItem" to item)
                )
            }
        }, POST_TYPE_SMALL)

        binding.rvBookmarks.apply {
            adapter = bookmarkListAdapter
            layoutManager = GridLayoutManager(activity, 3)
            addItemDecoration(SpacesItemDecoration(context))
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
            POST_TYPE_SMALL -> SpacesItemDecoration(this.requireContext())
            else -> LinearVerticalItemDecoration(this.requireContext())
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