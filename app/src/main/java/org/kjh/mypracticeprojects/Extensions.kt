package org.kjh.mypracticeprojects

import android.util.DisplayMetrics
import android.util.Patterns
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 * MyPracticeProjects
 * Class: extensions
 * Created by mac on 2021/09/30.
 *
 * Description:
 */

fun Int.dpToPx(displayMetrics: DisplayMetrics): Int = (this * displayMetrics.density).toInt()

fun String.isValidPattern(): Boolean =
    Patterns.EMAIL_ADDRESS.matcher(this).matches()