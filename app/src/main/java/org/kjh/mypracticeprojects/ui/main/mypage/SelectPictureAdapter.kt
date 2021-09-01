package org.kjh.mypracticeprojects.ui.main.mypage

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.kjh.mypracticeprojects.databinding.ItemGalleryImageBinding
import javax.annotation.Nullable

/**
 * MyPracticeProjects
 * Class: SelectPictureAdapter
 * Created by mac on 2021/08/31.
 *
 * Description:
 */
class SelectPictureAdapter: ListAdapter<MediaStoreImage, RecyclerView.ViewHolder>(imageDiffCallback) {
    init {
        setHasStableIds(true)
    }

    private lateinit var selectionTracker: SelectionTracker<Long>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = ItemGalleryImageBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)

        return MediaStoreImageViewHolder(view)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = currentList[position]
        when (holder) {
            is MediaStoreImageViewHolder -> holder.bind(item, position)
            else -> throw Exception("should not attach here")
        }
    }

    fun setSelectionTracker(selectionTracker: SelectionTracker<Long>) {
        this.selectionTracker = selectionTracker
    }

    inner class MediaStoreImageViewHolder(
        val binding: ItemGalleryImageBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(mediaStoreImage: MediaStoreImage, itemPosition: Int) {

            Glide.with(binding.image)
                .load(mediaStoreImage.contentUri)
                .thumbnail(0.33f)
                .centerCrop()
                .into(binding.image)

            binding.root.setOnClickListener {
                selectionTracker.select(itemPosition.toLong())
            }

            binding.rlOrderWrap.visibility =
                if (selectionTracker.isSelected(itemPosition.toLong())) View.VISIBLE else View.GONE

            binding.flSelectedBg.visibility =
                if (selectionTracker.isSelected(itemPosition.toLong())) View.VISIBLE else View.GONE

            if (selectionTracker.isSelected(itemPosition.toLong())) {
                val order = selectionTracker.selection.indexOf(itemPosition.toLong())
                binding.tvOrder.text = order.plus(1).toString()
            }
        }

        fun getItemDetails(viewHolder: RecyclerView.ViewHolder?): ItemDetailsLookup.ItemDetails<Long> {
            return object: ItemDetailsLookup.ItemDetails<Long>() {
                override fun getPosition(): Int {
                    if (viewHolder == null) {
                        return RecyclerView.NO_POSITION
                    }
                    return viewHolder.absoluteAdapterPosition
                }

                override fun getSelectionKey(): Long? {
                    return itemId
                }
            }
        }
    }
}

class MediaStoreImageDetailsLookup(
    private val recyclerView: RecyclerView
): ItemDetailsLookup<Long>() {

    @Nullable
    override fun getItemDetails(e: MotionEvent): ItemDetails<Long>? {
        val view = recyclerView.findChildViewUnder(e.x, e.y)
        if (view != null) {
            val viewHolder =
                recyclerView.getChildViewHolder(view) as SelectPictureAdapter.MediaStoreImageViewHolder
            return viewHolder.getItemDetails(viewHolder)
        }
        return null
    }
}

val imageDiffCallback = object: DiffUtil.ItemCallback<MediaStoreImage>() {
    override fun areItemsTheSame(oldItem: MediaStoreImage, newItem: MediaStoreImage): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MediaStoreImage, newItem: MediaStoreImage): Boolean {
        return oldItem == newItem
    }
}