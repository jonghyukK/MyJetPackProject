package org.kjh.mypracticeprojects.model

import androidx.recyclerview.widget.DiffUtil

/**
 * MyPracticeProjects
 * Class: AreaPostModel
 * Created by mac on 2021/09/02.
 *
 * Description:
 */
data class AreaInfoModel(
    val areaName: String,
    val areaImgUrl: String,
    val areaPostCount: Int
) {
    companion object {
        val DiffCallback = object: DiffUtil.ItemCallback<AreaInfoModel>() {
            override fun areItemsTheSame(oldItem: AreaInfoModel, newItem: AreaInfoModel): Boolean =
                oldItem.areaName == newItem.areaName

            override fun areContentsTheSame(oldItem: AreaInfoModel, newItem: AreaInfoModel): Boolean =
                oldItem == newItem
        }
    }
}