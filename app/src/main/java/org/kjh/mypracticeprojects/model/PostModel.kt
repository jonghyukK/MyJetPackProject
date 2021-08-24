package org.kjh.mypracticeprojects.model

import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * MyPracticeProjects
 * Class: PostModel
 * Created by mac on 2021/08/24.
 *
 * Description:
 */
data class PostModel(
    @SerializedName("postId")
    @Expose
    val postId: Int,

    @SerializedName("imageUrl")
    @Expose
    val imageUrl: String,

    @SerializedName("content")
    @Expose
    val content: String,

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
){
    companion object {
        val DiffCallback = object: DiffUtil.ItemCallback<PostModel>() {
            override fun areItemsTheSame(oldItem: PostModel, newItem: PostModel): Boolean =
                oldItem.postId == newItem.postId

            override fun areContentsTheSame(oldItem: PostModel, newItem: PostModel): Boolean =
                oldItem == newItem
        }
    }
}