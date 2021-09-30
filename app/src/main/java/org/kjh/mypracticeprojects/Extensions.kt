package org.kjh.mypracticeprojects

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Patterns
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

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

fun Fragment.replaceFragment(fm: FragmentManager, targetId: Int) =
    fm.beginTransaction()
        .replace(targetId, this)
        .commit()

fun Fragment.navigate(action: Int, bundle: Bundle? = null) {
    this.findNavController().navigate(action, bundle)
}

fun Fragment.navigate(navDir: NavDirections) {
    this.findNavController().navigate(navDir)
}
