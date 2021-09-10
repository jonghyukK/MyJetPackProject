package org.kjh.mypracticeprojects.model

import android.graphics.drawable.Drawable
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * MyPracticeProjects
 * Class: AreaModel
 * Created by mac on 2021/09/04.
 *
 * Description:
 */
@Parcelize
data class AreaModel(
    val areaName: String,
    val areaImg : Int
): Parcelable