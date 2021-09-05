package org.kjh.mypracticeprojects.ui.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.marginStart
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mypracticeprojects.AREA_LIST
import org.kjh.mypracticeprojects.databinding.ItemMainAreaBinding
import org.kjh.mypracticeprojects.model.AreaModel

/**
 * MyPracticeProjects
 * Class: LocalAreaListAdapter
 * Created by mac on 2021/09/03.
 *
 * Description:
 */
class LocalAreaListAdapter(
    val listener: LocalAreaListClickEventListener
): RecyclerView.Adapter<LocalAreaListAdapter.LocalAreaListViewHolder>() {
    private val areaList = AREA_LIST.map {
        AreaModel(
            areaName = it.key,
            areaImg = it.value
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocalAreaListViewHolder =
        LocalAreaListViewHolder(
            ItemMainAreaBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: LocalAreaListViewHolder, position: Int) {
        holder.bind(areaList[position])
    }

    inner class LocalAreaListViewHolder(
        val binding: ItemMainAreaBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AreaModel) {
            binding.areaModel   = item
            binding.ivAreaImg.setOnClickListener {
                listener.onClickArea(item)
            }
        }
    }

    override fun getItemCount(): Int = areaList.size
}

interface LocalAreaListClickEventListener {
    fun onClickArea(area: AreaModel)
}