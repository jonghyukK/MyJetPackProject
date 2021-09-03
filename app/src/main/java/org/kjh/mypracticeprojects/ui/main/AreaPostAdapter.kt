package org.kjh.mypracticeprojects.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mypracticeprojects.databinding.ItemAreaBinding
import org.kjh.mypracticeprojects.model.AreaInfoModel

/**
 * MyPracticeProjects
 * Class: AreaPostAdapter
 * Created by mac on 2021/09/02.
 *
 * Description:
 */
class AreaPostAdapter(
    val listener: AreaClickEventListener
): ListAdapter<AreaInfoModel, AreaPostAdapter.AreaPostViewHolder>(AreaInfoModel.DiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AreaPostAdapter.AreaPostViewHolder =
        AreaPostViewHolder(
            ItemAreaBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: AreaPostAdapter.AreaPostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class AreaPostViewHolder(val binding: ItemAreaBinding)
        : RecyclerView.ViewHolder(binding.root) {
            fun bind(item: AreaInfoModel) {
                binding.areaItem = item
                binding.ivAreaImg.setOnClickListener {
                    listener.onClickArea(item)
                }
            }
        }
}

interface AreaClickEventListener {
    fun onClickArea(item: AreaInfoModel)
}