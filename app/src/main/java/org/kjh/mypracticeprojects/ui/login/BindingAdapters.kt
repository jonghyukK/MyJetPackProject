package org.kjh.mypracticeprojects.ui.login

import android.view.View
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.orhanobut.logger.Logger

/**
 * MyPracticeProjects
 * Class: BindingAdapters
 * Created by mac on 2021/07/30.
 *
 * Description:
 */

object BindingAdapters {

    @JvmStatic
    @BindingAdapter(value = ["isValid", "errorMsg"])
    fun setError(view: TextInputLayout, isValid: Boolean?, errorMsg: String) {
        isValid?.run {
            view.apply {
                if (isValid) {
                    error = null
                    isErrorEnabled = false
                } else
                    error = errorMsg
            }
        }
    }


    @JvmStatic
    @BindingAdapter("app:isLoading")
    fun isLoading(view: ProgressBar, isLoading: Boolean) {
        view.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}