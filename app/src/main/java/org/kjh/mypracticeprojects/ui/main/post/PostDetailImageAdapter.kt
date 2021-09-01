package org.kjh.mypracticeprojects.ui.main.post

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.kjh.mypracticeprojects.databinding.ItemPostDetailImageBinding

/**
 * MyPracticeProjects
 * Class: PostDetailImageAdapter
 * Created by mac on 2021/09/01.
 *
 * Description:
 */
class PostDetailImageAdapter
    : RecyclerView.Adapter<PostDetailImageAdapter.PostDetailViewHolder>() {
    private lateinit var imageUrls: List<String>

    fun setImageList(imgList: List<String>) {
        this.imageUrls = imgList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostDetailViewHolder =
        PostDetailViewHolder(
            ItemPostDetailImageBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )


    override fun onBindViewHolder(holder: PostDetailViewHolder, position: Int) {
        holder.bind(imageUrls[position], position)
    }

    inner class PostDetailViewHolder(
        val binding: ItemPostDetailImageBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(imgUrl: String, position: Int) {
            Glide.with(binding.ivPostImage)
                .load(imgUrl)
                .thumbnail(0.33f)
                .centerCrop()
                .into(binding.ivPostImage)
        }
    }

    override fun getItemCount(): Int = imageUrls.size
}