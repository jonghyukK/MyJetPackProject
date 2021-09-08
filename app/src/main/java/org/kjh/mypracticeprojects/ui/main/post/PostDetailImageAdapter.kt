package org.kjh.mypracticeprojects.ui.main.post

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.orhanobut.logger.Logger
import org.kjh.mypracticeprojects.R
import org.kjh.mypracticeprojects.databinding.ItemPostDetailImageBinding
import org.kjh.mypracticeprojects.model.PostModel

/**
 * MyPracticeProjects
 * Class: PostDetailImageAdapter
 * Created by mac on 2021/09/01.
 *
 * Description:
 */
class PostDetailImageAdapter(val onClick: () -> Unit)
    : RecyclerView.Adapter<PostDetailImageAdapter.PostDetailViewHolder>() {
    private lateinit var imageUrls: List<String>

    fun setImageList(imgList: List<String>) {
        this.imageUrls = imgList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostDetailViewHolder =
        PostDetailViewHolder(
            ItemPostDetailImageBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onClick)


    override fun onBindViewHolder(holder: PostDetailViewHolder, position: Int) {
        holder.bind(imageUrls[position], position)
    }

    inner class PostDetailViewHolder(
        val binding: ItemPostDetailImageBinding,
        onClick: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(imgUrl: String, position: Int) {
            Glide.with(binding.ivPostImage)
                .load(imgUrl)
                .thumbnail(0.33f)
                .centerCrop()
                .into(binding.ivPostImage)
        }

        init {
            binding.ivPostImage.setOnClickListener {
                onClick()
            }
        }
    }

    override fun getItemCount(): Int = imageUrls.size
}