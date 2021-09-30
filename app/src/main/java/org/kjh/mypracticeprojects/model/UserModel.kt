package org.kjh.mypracticeprojects.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * MyPracticeProjects
 * Class: UserModel
 * Created by mac on 2021/07/22.
 *
 * Description:
 */
data class UserModel(
    @SerializedName("email")
    @Expose
    var email: String,

    @SerializedName("postCount")
    @Expose
    var postCount: Int = 0,

    @SerializedName("followingCount")
    @Expose
    var followingCount: Int = 0,

    @SerializedName("followCount")
    @Expose
    var followCount: Int = 0,

    @SerializedName("profileImg")
    @Expose
    var profileImg: String? = null,

    @SerializedName("bookMarks")
    @Expose
    var bookMarks: List<PostModel>? = listOf(),

    @SerializedName("posts")
    @Expose
    var posts: Map<String, List<PostModel>> = mapOf(),
)