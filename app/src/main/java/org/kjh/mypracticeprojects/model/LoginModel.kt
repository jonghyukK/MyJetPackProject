package org.kjh.mypracticeprojects.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * MyPracticeProjects
 * Class: LoginModel
 * Created by mac on 2021/08/03.
 *
 * Description:
 */
data class LoginModel(
    @SerializedName("email")
    @Expose
    var email: String,

    @SerializedName("pw")
    @Expose
    var pw: String
)