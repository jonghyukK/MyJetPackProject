package org.kjh.mypracticeprojects.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.orhanobut.logger.Logger
import dagger.hilt.android.AndroidEntryPoint
import org.kjh.mypracticeprojects.R
import org.kjh.mypracticeprojects.databinding.FragmentAreaPostListBinding
import org.kjh.mypracticeprojects.model.AreaInfoModel
import org.kjh.mypracticeprojects.model.PostModel
import org.kjh.mypracticeprojects.ui.base.BaseFragment
import org.kjh.mypracticeprojects.ui.main.mypage.MyPageFragmentDirections
import org.kjh.mypracticeprojects.util.DataState
import org.kjh.mypracticeprojects.util.SpacesTwoColumnItemDecoration


@AndroidEntryPoint
class AreaPostListFragment :
    BaseFragment<FragmentAreaPostListBinding>(R.layout.fragment_area_post_list),
    AreaClickEventListener
{
    companion object {
        const val FAILED_FETCH_AREA_POST_LIST = "Failed Fetch Area Post List."
    }

    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var areaPostAdapter: AreaPostAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainViewModel = mainViewModel

        initAreaPostListRecyclerView()

        mainViewModel.myUserData.observe(viewLifecycleOwner, { myUserData ->
            when (myUserData) {
                is DataState.Success -> {
                    myUserData.data?.let {
                        makeAreaPostDataAndSubmitList(it.posts)
                    }
                }

                is DataState.Error ->
                    Toast.makeText(requireContext(), FAILED_FETCH_AREA_POST_LIST, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initAreaPostListRecyclerView() {
        areaPostAdapter = AreaPostAdapter(this@AreaPostListFragment)

        binding.rvAreaList.apply {
            adapter = areaPostAdapter
            layoutManager = GridLayoutManager(activity, 2)
            addItemDecoration(SpacesTwoColumnItemDecoration(requireContext()))
        }
    }

    private fun makeAreaPostDataAndSubmitList(userPosts: Map<String, List<PostModel>>) {
        if (userPosts.keys.isNotEmpty()) {
            val areaInfoList = mutableListOf<AreaInfoModel>()

            for ((key, value) in userPosts) {
                areaInfoList.add(
                    AreaInfoModel(
                        areaName = key,
                        areaImgUrl = value[0].imageUrl[0],
                        areaPostCount = value.size
                    )
                )
            }
            areaPostAdapter.submitList(areaInfoList)
        }
    }

    override fun onClickArea(item: AreaInfoModel) {
        val action = MyPageFragmentDirections.actionMyPageFragmentToPostListFragment(item.areaName)
        findNavController().navigate(action)
    }
}