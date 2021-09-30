package org.kjh.mypracticeprojects.ui.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mypracticeprojects.R
import org.kjh.mypracticeprojects.databinding.ItemMainAreaBinding
import org.kjh.mypracticeprojects.model.CityModel

/**
 * MyPracticeProjects
 * Class: LocalAreaListAdapter
 * Created by mac on 2021/09/03.
 *
 * Description:
 */

val CITY_ITEMS = mapOf(
    "서울" to R.drawable.local_img_seoul,
    "경기" to R.drawable.local_img_gyeonggido,
    "인천" to R.drawable.local_img_incheon,
    "충북" to R.drawable.local_img_chungbook,
    "충남" to R.drawable.local_img_chungnam,
    "경북" to R.drawable.local_img_gyeongbuk,
    "경남" to R.drawable.local_img_gyeongnam,
    "전북" to R.drawable.local_img_jeunbook,
    "전남" to R.drawable.local_img_jeonnam,
    "강원" to R.drawable.local_img_gangwon
)

class LocalAreaListAdapter(
    val listener: LocalAreaListClickEventListener
): RecyclerView.Adapter<LocalAreaListAdapter.LocalAreaListViewHolder>() {
    private val areaList = CITY_ITEMS.map {
        CityModel(
            cityName = it.key,
            cityImg = it.value
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocalAreaListViewHolder =
        LocalAreaListViewHolder(
            ItemMainAreaBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: LocalAreaListViewHolder, position: Int) {
        holder.bind(areaList[position])
    }

    inner class LocalAreaListViewHolder(
        val binding: ItemMainAreaBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CityModel) {
            binding.cityModel   = item
            binding.ivAreaImg.setOnClickListener {
                listener.onClickCity(item)
            }
        }
    }

    override fun getItemCount(): Int = areaList.size
}

interface LocalAreaListClickEventListener {
    fun onClickCity(area: CityModel)
}