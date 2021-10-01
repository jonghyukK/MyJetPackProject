package org.kjh.mypracticeprojects.model

import androidx.recyclerview.widget.DiffUtil

/**
 * MyPracticeProjects
 * Class: AreaPostModel
 * Created by mac on 2021/09/02.
 *
 * Description:
 */
data class CityInfoModel(
    val cityName     : String,
    val cityImgUrl   : String,
    val cityPostCount: Int = 0
) {
    companion object {
        val DiffCallback = object: DiffUtil.ItemCallback<CityInfoModel>() {
            override fun areItemsTheSame(
                oldItem: CityInfoModel,
                newItem: CityInfoModel
            ): Boolean = oldItem.cityName == newItem.cityName

            override fun areContentsTheSame(
                oldItem: CityInfoModel,
                newItem: CityInfoModel
            ): Boolean = oldItem == newItem
        }
    }
}