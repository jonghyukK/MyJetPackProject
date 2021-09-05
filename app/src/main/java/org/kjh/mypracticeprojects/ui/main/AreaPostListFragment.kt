package org.kjh.mypracticeprojects.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.orhanobut.logger.Logger
import dagger.hilt.android.AndroidEntryPoint
import org.kjh.mypracticeprojects.R
import org.kjh.mypracticeprojects.databinding.FragmentAreaPostListBinding
import org.kjh.mypracticeprojects.model.AreaInfoModel
import org.kjh.mypracticeprojects.ui.base.BaseFragment
import org.kjh.mypracticeprojects.ui.main.mypage.MyPageFragmentDirections
import org.kjh.mypracticeprojects.util.SpacesTwoColumnItemDecoration


@AndroidEntryPoint
class AreaPostListFragment :
    BaseFragment<FragmentAreaPostListBinding>(R.layout.fragment_area_post_list),
    AreaClickEventListener
{
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val areaPostListAdapter = AreaPostAdapter(this)

        with (binding.rvAreaList) {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = areaPostListAdapter
            addItemDecoration(SpacesTwoColumnItemDecoration(this.context))
        }

        mainViewModel.myUserData.observe(viewLifecycleOwner, { myUserData ->
            val userPosts = myUserData.posts

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
                areaPostListAdapter.submitList(areaInfoList)
            }
        })
    }

    override fun onClickArea(item: AreaInfoModel) {
        val action = MyPageFragmentDirections.actionMyPageFragmentToPostListFragment(item.areaName)
        findNavController().navigate(action)
    }
}