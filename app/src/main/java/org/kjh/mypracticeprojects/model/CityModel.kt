package org.kjh.mypracticeprojects.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * MyPracticeProjects
 * Class: CityModel
 * Created by mac on 2021/09/04.
 *
 * Description:
 */
@Parcelize
data class CityModel(
    val cityName: String,
    val cityImg : Int
): Parcelable