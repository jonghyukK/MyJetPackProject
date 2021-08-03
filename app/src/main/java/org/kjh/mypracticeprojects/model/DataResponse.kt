package org.kjh.mypracticeprojects.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * MyPracticeProjects
 * Class: DataResponse
 * Created by mac on 2021/08/03.
 *
 * Description:
 */
data class DataResponse(
    @SerializedName("result")
    @Expose
    var result: String,

    @SerializedName("errorMsg")
    @Expose
    var errorMsg: String,
)