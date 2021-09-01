package org.kjh.mypracticeprojects.ui

import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import org.kjh.mypracticeprojects.ui.login.FocusEventHandler
import org.kjh.mypracticeprojects.util.GlideApp

/**
 * MyPracticeProjects
 * Class: BindingAdapters
 * Created by mac on 2021/07/30.
 *
 * Description:
 */

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("app:isLoading")
    fun isLoading(view: ProgressBar, isLoading: Boolean) {
        view.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("app:visibility")
    fun setVisibility(view: View, show: Boolean?) {
        show?.run {
            view.apply {
                visibility = if (show) View.VISIBLE else View.INVISIBLE
            }
        }
    }

    @JvmStatic
    @BindingAdapter("app:visibility")
    fun setErrorVisibility(view: TextView, errorMsg: String?) {
        view.apply {
            visibility = if (errorMsg.isNullOrBlank()) View.INVISIBLE else View.VISIBLE
            text = errorMsg
        }
    }

    @JvmStatic
    @BindingAdapter("app:onFocusLost")
    fun EditText.onFocusLost(callback: FocusEventHandler) {
        setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) { callback.onFocusLost(this) }
        }
    }

    @JvmStatic
    @BindingAdapter("app:imgUri")
    fun bindImageWithUri(view: ImageView, imgUri: Uri?) {
        imgUri?.run {
            GlideApp.with(view)
                .load(imgUri)
                .thumbnail(0.33f)
                .centerCrop()
                .into(view)
        }
    }

    @JvmStatic
    @BindingAdapter("app:imgUrl")
    fun bindImageWithUrl(view: ImageView, imgUrl: String?) {
        imgUrl?.run {
            GlideApp.with(view)
                .load(imgUrl)
                .thumbnail(0.33f)
                .centerCrop()
                .into(view)
        }
    }
}