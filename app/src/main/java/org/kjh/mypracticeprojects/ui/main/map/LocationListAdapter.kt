package org.kjh.mypracticeprojects.ui.main.map

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mypracticeprojects.R
import org.kjh.mypracticeprojects.databinding.ItemLocationListBinding
import org.kjh.mypracticeprojects.model.LocationItem

/**
 * MyPracticeProjects
 * Class: LocationListAdapter
 * Created by mac on 2021/10/01.
 *
 * Description:
 */
class LocationListAdapter(
    private val onClick: (LocationItem) -> Unit)
: ListAdapter<LocationItem, LocationListAdapter.LocationViewHolder>(LocationItem.DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LocationViewHolder(
            ItemLocationListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class LocationViewHolder(
        val binding: ItemLocationListBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(locationItem: LocationItem) {
            binding.locationItem = locationItem
            binding.clLocationItem.setOnClickListener {
                onClick(locationItem)
            }
        }
    }
}
