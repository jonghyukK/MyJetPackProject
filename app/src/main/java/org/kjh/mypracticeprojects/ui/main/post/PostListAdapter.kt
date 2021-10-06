package org.kjh.mypracticeprojects.ui.main.post

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.children
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.ORIENTATION_HORIZONTAL
import org.kjh.mypracticeprojects.databinding.ItemPostLargeBinding
import org.kjh.mypracticeprojects.databinding.ItemPostMediumMultipleBinding
import org.kjh.mypracticeprojects.databinding.ItemPostMediumSingleBinding
import org.kjh.mypracticeprojects.databinding.ItemPostSmallBinding
import org.kjh.mypracticeprojects.dpToPx
import org.kjh.mypracticeprojects.model.PostModel
import org.kjh.mypracticeprojects.ui.main.post.PostDetailImageAdapter


/**
 * MyPracticeProjects
 * Class: PostListAdapter
 * Created by mac on 2021/09/07.
 *
 * Description:
 */

const val POST_TYPE_SMALL  = 0
const val POST_TYPE_MEDIUM = 1
const val POST_TYPE_LARGE  = 2
const val POST_TYPE_MEDIUM_SINGLE = 11
const val POST_TYPE_MEDIUM_MULTIPLE = 12

class PostListAdapter(
    val listener: PostListClickEventListener,
    var postViewType: Int = 0
): ListAdapter<PostModel, RecyclerView.ViewHolder>(PostModel.DiffCallback) {

    fun changePostViewType(viewType: Int) {
        postViewType = viewType
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int =
        when (postViewType) {
            POST_TYPE_MEDIUM ->
                if (getItem(position).imageUrl.size > 2)
                    POST_TYPE_MEDIUM_MULTIPLE else POST_TYPE_MEDIUM_SINGLE
            else -> postViewType
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            POST_TYPE_LARGE -> PostLargeViewHolder(
                ItemPostLargeBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            POST_TYPE_MEDIUM_SINGLE -> PostMediumSingleViewHolder(
                ItemPostMediumSingleBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            POST_TYPE_MEDIUM_MULTIPLE -> PostMediumMultipleViewHolder(
                ItemPostMediumMultipleBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> PostSmallViewHolder(
                ItemPostSmallBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        when (holder.itemViewType) {
            POST_TYPE_LARGE           -> (holder as PostLargeViewHolder).bind(item)
            POST_TYPE_MEDIUM_SINGLE   -> (holder as PostMediumSingleViewHolder).bind(item)
            POST_TYPE_MEDIUM_MULTIPLE -> (holder as PostMediumMultipleViewHolder).bind(item)
            else -> (holder as PostSmallViewHolder).bind(item)
        }
    }

    inner class PostSmallViewHolder(val binding: ItemPostSmallBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PostModel) {
            binding.postModel = item
            binding.cvPostItem.setOnClickListener {
                listener.onClickPost(item)
            }
        }
    }

    inner class PostMediumSingleViewHolder(val binding: ItemPostMediumSingleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PostModel) {
            binding.postModel = item
            binding.cvPostItem.setOnClickListener {
                listener.onClickPost(item)
            }
        }
    }

    inner class PostMediumMultipleViewHolder(val binding: ItemPostMediumMultipleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PostModel) {
            binding.postModel = item
            binding.cvPostItem.setOnClickListener {
                listener.onClickPost(item)
            }
        }
    }

    inner class PostLargeViewHolder(val binding: ItemPostLargeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PostModel) {
            binding.postModel = item
            binding.cvPostItem.setOnClickListener {
                listener.onClickPost(item)
            }

            binding.tvImageCount.text = String.format("%d/%d", 1, item.imageUrl.size)

            binding.vpPager.apply {
                orientation = ORIENTATION_HORIZONTAL
                offscreenPageLimit = 4
                adapter = PostDetailImageAdapter {
                    listener.onClickPost(item)
                }.apply {
                    setImageList(item.imageUrl)
                    registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                        override fun onPageSelected(position: Int) {
                            binding.tvImageCount.text =
                                String.format("%d/%d", position + 1, item.imageUrl.size)
                        }
                    })
                }

                children.find { it is RecyclerView }?.let {
                    (it as RecyclerView).isNestedScrollingEnabled = false
                }

                val offsetPx = 20.dpToPx(resources.displayMetrics)
                setPadding(offsetPx, 0, offsetPx, 0)

                setPageTransformer(MarginPageTransformer(8.dpToPx(resources.displayMetrics)))
            }
        }
    }
}

interface PostListClickEventListener {
    fun onClickPost(item: PostModel)
}