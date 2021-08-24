package org.kjh.mypracticeprojects.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * MyPracticeProjects
 * Class: LocationResponse
 * Created by mac on 2021/08/17.
 *
 * Description:
 */

data class LocationResponse(
    @SerializedName("documents")
    @Expose
    val documents: List<LocationItem>
)

@Parcelize
data class LocationItem(

    @SerializedName("address_name")
    @Expose
    val address_name: String,

    @SerializedName("category_group_code")
    @Expose
    val category_group_code: String,

    @SerializedName("category_group_name")
    @Expose
    val category_group_name: String,

    @SerializedName("category_name")
    @Expose
    val category_name: String,

    @SerializedName("phone")
    @Expose
    val phone: String,

    @SerializedName("place_name")
    @Expose
    val place_name: String,

    @SerializedName("place_url")
    @Expose
    val place_url: String,

    @SerializedName("road_address_name")
    @Expose
    val road_address_name: String,

    @SerializedName("x")
    @Expose
    val x: String,

    @SerializedName("y")
    @Expose
    val y: String
): Parcelable {
    companion object {
        val DiffCallback = object: DiffUtil.ItemCallback<LocationItem>() {
            override fun areItemsTheSame(oldItem: LocationItem, newItem: LocationItem): Boolean =
                oldItem.place_name == newItem.place_name

            override fun areContentsTheSame(oldItem: LocationItem, newItem: LocationItem): Boolean =
                oldItem == newItem
        }
    }
}