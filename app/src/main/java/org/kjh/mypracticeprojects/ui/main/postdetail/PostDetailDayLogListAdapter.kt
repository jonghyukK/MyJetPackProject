package org.kjh.mypracticeprojects.ui.main.postdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.children
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.orhanobut.logger.Logger
import org.kjh.mypracticeprojects.databinding.ItemPostDetailDayLogBinding
import org.kjh.mypracticeprojects.databinding.ItemPostDetailImageBinding
import org.kjh.mypracticeprojects.model.PostModel
import org.kjh.mypracticeprojects.util.GlideApp

/**
 * MyPracticeProjects
 * Class: PostDetailDayLogListAdapter
 * Created by mac on 2021/09/08.
 *
 * Description:
 */
class PostDetailDayLogListAdapter
    : ListAdapter<PostModel, PostDetailDayLogListAdapter.PostDetailDayLogViewHolder>(PostModel.DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostDetailDayLogViewHolder =
        PostDetailDayLogViewHolder(
            ItemPostDetailDayLogBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: PostDetailDayLogViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PostDetailDayLogViewHolder(val binding: ItemPostDetailDayLogBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PostModel) {
            binding.postModel = item
            binding.rvPostDetailDayLog.apply {
                layoutManager =
                    LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL,false)
                adapter = PostDetailDayLogImageAdapter().apply {
                    setImgList(item.imageUrl)
                }
                isNestedScrollingEnabled = false
            }

            PagerSnapHelper().apply {
                attachToRecyclerView(binding.rvPostDetailDayLog)
            }
        }
    }
}

class PostDetailDayLogImageAdapter
    : RecyclerView.Adapter<PostDetailDayLogImageAdapter.PostDetailDayLogImageViewHolder>() {
    private lateinit var imgUrls: List<String>

    fun setImgList(imgList: List<String>) {
        this.imgUrls = imgList
    }

    override fun getItemCount(): Int = imgUrls.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = PostDetailDayLogImageViewHolder(
        ItemPostDetailImageBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: PostDetailDayLogImageViewHolder, position: Int) {
        holder.bind(imgUrls[position])
    }

    inner class PostDetailDayLogImageViewHolder(
        val binding: ItemPostDetailImageBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(imgUrl: String) {
            GlideApp.with(binding.ivPostImage)
                .load(imgUrl)
                .thumbnail(0.33f)
                .centerCrop()
                .into(binding.ivPostImage)
        }
    }
}