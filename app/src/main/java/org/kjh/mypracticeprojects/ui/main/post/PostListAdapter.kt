package org.kjh.mypracticeprojects.ui.main.post

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mypracticeprojects.databinding.ItemMyImageBinding
import org.kjh.mypracticeprojects.model.PostModel

/**
 * MyPracticeProjects
 * Class: PostListAdapter
 * Created by mac on 2021/09/01.
 *
 * Description:
 */
class PostListAdapter(
    val listener: PostListClickEventListener
) : ListAdapter<PostModel, PostListAdapter.PostListViewHolder>(PostModel.DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostListViewHolder =
        PostListViewHolder(
            ItemMyImageBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: PostListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PostListViewHolder(val binding: ItemMyImageBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PostModel) {
            binding.postItem = item
            binding.ivPostListImage.setOnClickListener {
                listener.onClickPost(item)
            }
        }
    }
}

interface PostListClickEventListener {
    fun onClickPost(postItem: PostModel)
}