package org.kjh.mypracticeprojects.ui.main.postdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import org.kjh.mypracticeprojects.R
import org.kjh.mypracticeprojects.databinding.FragmentPostDetailDayLogBinding
import org.kjh.mypracticeprojects.ui.base.BaseFragment
import org.kjh.mypracticeprojects.util.DataState
import org.kjh.mypracticeprojects.util.LinearVerticalItemDecoration

@AndroidEntryPoint
class PostDetailDayLogFragment
    : BaseFragment<FragmentPostDetailDayLogBinding>(R.layout.fragment_post_detail_day_log) {

    private val viewModel: PostDetailViewModel by activityViewModels()
    private lateinit var postDetailListAdapter: PostDetailDayLogListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        viewModel.postListByPlace.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is DataState.Success -> postDetailListAdapter.submitList(dataState.data)
            }
        })
    }

    private fun initRecyclerView() {
        postDetailListAdapter = PostDetailDayLogListAdapter()
        binding.rvPostDayLog.apply {
            adapter = postDetailListAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(LinearVerticalItemDecoration(this.context))
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = PostDetailDayLogFragment()
    }
}