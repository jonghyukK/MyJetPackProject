package org.kjh.mypracticeprojects.ui.main.postdetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import org.kjh.mypracticeprojects.R
import org.kjh.mypracticeprojects.databinding.FragmentPostDetailDayLogBinding
import org.kjh.mypracticeprojects.ui.base.BaseFragment
import org.kjh.mypracticeprojects.util.DataState
import org.kjh.mypracticeprojects.ui.common.LinearVerticalItemDecoration

@AndroidEntryPoint
class PostDetailDayLogFragment
    : BaseFragment<FragmentPostDetailDayLogBinding>(R.layout.fragment_post_detail_day_log) {

    private val viewModel: PostDetailViewModel by activityViewModels()
    private lateinit var postDetailListAdapter: PostDetailDayLogListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        subscribeObserver()
    }

    private fun subscribeObserver() {
        viewModel.postListByPlace.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is DataState.Success -> postDetailListAdapter.submitList(dataState.data)
            }
        })
    }

    private fun initRecyclerView() {
        postDetailListAdapter = PostDetailDayLogListAdapter()
        binding.rvPostDayLog.apply {
            adapter       = postDetailListAdapter
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(LinearVerticalItemDecoration(requireContext()))
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = PostDetailDayLogFragment()
    }
}