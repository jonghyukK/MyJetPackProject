package org.kjh.mypracticeprojects.ui.main.post

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import org.kjh.mypracticeprojects.R
import org.kjh.mypracticeprojects.databinding.FragmentPostSmallListBinding
import org.kjh.mypracticeprojects.model.PostModel
import org.kjh.mypracticeprojects.ui.base.BaseFragment
import org.kjh.mypracticeprojects.ui.main.MainViewModel
import org.kjh.mypracticeprojects.ui.main.PostSmallListAdapter
import org.kjh.mypracticeprojects.ui.main.PostSmallListClickEventListener
import org.kjh.mypracticeprojects.util.LinearVerticalItemDecoration

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class PostSmallListFragment :
    BaseFragment<FragmentPostSmallListBinding>(R.layout.fragment_post_small_list)
{

    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var postSmallListAdapter: PostSmallListAdapter
    private var param1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()

        mainViewModel.recentPostData.observe(viewLifecycleOwner, { recentPostList ->
            postSmallListAdapter.submitList(recentPostList)
        })
    }

    private fun initRecyclerView() {
        postSmallListAdapter = PostSmallListAdapter(object: PostSmallListClickEventListener {
            override fun onClickPostSmall(item: PostModel) {
                val bundle = bundleOf(
                    "postList" to mainViewModel.recentPostData.value,
                    "postId" to item.postId
                )

                findNavController().navigate(
                    R.id.action_homeFragment_to_postDetailFragment,
                    bundle
                )
            }
        })

        with (binding.rvPostSmallList) {
            adapter = postSmallListAdapter
            layoutManager = LinearLayoutManager(context).apply {
                orientation = LinearLayoutManager.VERTICAL
                addItemDecoration(LinearVerticalItemDecoration(context))
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            PostSmallListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}