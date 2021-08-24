package org.kjh.mypracticeprojects.ui.main.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.orhanobut.logger.Logger
import org.kjh.mypracticeprojects.R
import org.kjh.mypracticeprojects.databinding.FragmentAreaImageBinding
import org.kjh.mypracticeprojects.model.PostModel
import org.kjh.mypracticeprojects.ui.base.BaseFragment
import org.kjh.mypracticeprojects.util.GlideApp
import org.kjh.mypracticeprojects.util.SpacesItemDecoration

/**
 * MyPracticeProjects
 * Class: AreaImageFragment
 * Created by mac on 2021/08/16.
 *
 * Description:
 */
class AreaImageFragment: BaseFragment<FragmentAreaImageBinding>(R.layout.fragment_area_image) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey("TEST") }?.apply {
//            binding.tvAreaImageText.text = getInt("TEST").toString()
//            Logger.d("This is : ${getInt("TEST").toString()}")
        }

        val myImagesAdapter = MyImagesAdapter { content ->
            Logger.d("onClicked content : $content")
        }

        binding.rvMyImages.apply {
            layoutManager = GridLayoutManager(activity, 3)
            adapter = myImagesAdapter
            addItemDecoration(SpacesItemDecoration(this.context))
        }
    }

    private inner class MyImagesAdapter(val onClick: (PostModel) -> Unit) :
        ListAdapter<PostModel, MyImageViewHolder>(PostModel.DiffCallback) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyImageViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_my_image, parent, false)
            return MyImageViewHolder(view, onClick)
        }

        override fun onBindViewHolder(holder: MyImageViewHolder, position: Int) {
            val contentItem = getItem(position)
            holder.rootView.tag = contentItem

            GlideApp.with(holder.imageView)
                .load(contentItem.imageUrl)
                .thumbnail(0.33f)
                .centerCrop()
                .into(holder.imageView)
        }
    }
}

private class MyImageViewHolder(view: View, onClick: (PostModel) -> Unit) :
    RecyclerView.ViewHolder(view) {
    val rootView = view
    val imageView: ImageView = view.findViewById(R.id.iv_myImage)

    init {
        imageView.setOnClickListener {
            val image = rootView.tag as? PostModel ?: return@setOnClickListener
            onClick(image)
        }
    }
}