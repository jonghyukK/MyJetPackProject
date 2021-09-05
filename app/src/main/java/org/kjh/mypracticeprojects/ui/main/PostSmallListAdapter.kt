package org.kjh.mypracticeprojects.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mypracticeprojects.databinding.ItemPostSmallBinding
import org.kjh.mypracticeprojects.model.PostModel

/**
 * MyPracticeProjects
 * Class: PostSmallListAdapter
 * Created by mac on 2021/09/04.
 *
 * Description:
 */
class PostSmallListAdapter(
    val listener: PostSmallListClickEventListener
): ListAdapter<PostModel, PostSmallListAdapter.PostSmallListViewHolder>(PostModel.DiffCallback){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostSmallListViewHolder {
        val view = ItemPostSmallBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return PostSmallListViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostSmallListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PostSmallListViewHolder(val binding: ItemPostSmallBinding)
        : RecyclerView.ViewHolder(binding.root) {

            fun bind(item: PostModel) {
                binding.postModel = item
                binding.cvPostSmallItem.setOnClickListener {
                    listener.onClickPostSmall(item)
                }
            }
        }
}

interface PostSmallListClickEventListener {
    fun onClickPostSmall(item: PostModel)
}