package org.kjh.mypracticeprojects.ui.main.post

import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.orhanobut.logger.Logger
import org.kjh.mypracticeprojects.MyApplication
import org.kjh.mypracticeprojects.PREF_KEY_LOGIN_ID
import org.kjh.mypracticeprojects.databinding.ItemPostDetailListBinding
import org.kjh.mypracticeprojects.model.PostModel

/**
 * MyPracticeProjects
 * Class: PostDetailListAdapter
 * Created by mac on 2021/09/03.
 *
 * Description:
 */
class PostDetailListAdapter(
    val listener : PostDetailClickEventListener
) : ListAdapter<PostModel, PostDetailListAdapter.PostDetailListViewHolder>(PostModel.DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PostDetailListViewHolder(
            ItemPostDetailListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: PostDetailListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PostDetailListViewHolder(val binding: ItemPostDetailListBinding) :
        RecyclerView.ViewHolder(binding.root)
    {

        fun bind(item: PostModel) {
            binding.postModel = item
            binding.tvImageCount.text = String.format("%d/%d", 1, item.imageUrl.size)

            with(binding.vpPostDetail) {
                adapter = PostDetailImageAdapter {
                    
                }.apply {
                    setImageList(item.imageUrl)

                    registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                        override fun onPageSelected(position: Int) {
                            binding.tvImageCount.text =
                                String.format("%d/%d", position + 1, item.imageUrl.size)
                        }
                    })
                }
            }

            binding.rlLocation.setOnClickListener {
                listener.onClickMap(item)
            }

            with(binding.btnMore) {
                visibility = if (MyApplication.prefs.getPref(PREF_KEY_LOGIN_ID, "") == item.email)
                    View.VISIBLE
                 else
                    View.GONE

                setOnClickListener {
                    listener.onClickMore(item.postId)
                }
            }
        }
    }
}

interface PostDetailClickEventListener {
    fun onClickMap(item: PostModel)
    fun onClickMore(postId: Int)
}