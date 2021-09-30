package org.kjh.mypracticeprojects.ui.main.mypage

import android.net.Uri
import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.parcelize.Parcelize
import java.io.Serializable
import java.util.*

/**
 * MyPracticeProjects
 * Class: MediaStoreImage
 * Created by mac on 2021/08/10.
 *
 * Description:
 */

@Parcelize
data class MediaStoreImage(
    val id          : Long,
    val displayName : String,
    val dateAdded   : Date,
    val contentUri  : Uri,
    val realPath    : String,
) : Parcelable {
    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<MediaStoreImage>() {
            override fun areItemsTheSame(oldItem: MediaStoreImage, newItem: MediaStoreImage) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: MediaStoreImage, newItem: MediaStoreImage) =
                oldItem == newItem
        }
    }
}