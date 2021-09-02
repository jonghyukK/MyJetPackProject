package org.kjh.mypracticeprojects.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * MyPracticeProjects
 * Class: PostResponse
 * Created by mac on 2021/09/01.
 *
 * Description:
 */
data class PostResponse(
    @SerializedName("result")
    @Expose
    var result: String,

    @SerializedName("errorMsg")
    @Expose
    var errorMsg: String,

    @SerializedName("postId")
    @Expose
    var postId: Int,
)