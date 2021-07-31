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
    var email: String?,

    @SerializedName("pw")
    @Expose
    var pw: String?,

    @SerializedName("pwConfirm")
    @Expose
    var pwConfirm: String?,
)

data class UserResponse(

    @SerializedName("result")
    @Expose
    var result: String,

    @SerializedName("errorMsg")
    @Expose
    var errorMsg: String,
)