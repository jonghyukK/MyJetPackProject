package org.kjh.mypracticeprojects.model

import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.kjh.mypracticeprojects.ui.main.mypage.MediaStoreImage

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

    @SerializedName("pw")
    @Expose
    var pw: String,

    @SerializedName("postCount")
    @Expose
    var postCount: Int,

    @SerializedName("followingCount")
    @Expose
    var followingCount: Int,

    @SerializedName("followCount")
    @Expose
    var followCount: Int,

    @SerializedName("profileImg")
    @Expose
    var profileImg: String? = null,

    @SerializedName("posts")
    @Expose
    var posts: List<PostModel>?,
)