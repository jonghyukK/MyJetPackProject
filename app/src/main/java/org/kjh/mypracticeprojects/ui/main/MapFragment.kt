package org.kjh.mypracticeprojects.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import org.kjh.mypracticeprojects.R
import org.kjh.mypracticeprojects.databinding.FragmentMapBinding
import org.kjh.mypracticeprojects.model.LocationItem
import org.kjh.mypracticeprojects.ui.MainViewModel
import org.kjh.mypracticeprojects.ui.base.BaseFragment

@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding>(R.layout.fragment_map) {

    private val viewModel: MapViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var mapView: MapView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        initToolbarWithNavigation()
        initMapView()

        val locationAdapter = LocationAdapter {
            mainViewModel.setUploadLocationData(it)
            drawMapPoint(it)
        }

        binding.rvLocationList.apply {
            adapter = locationAdapter
        }

        viewModel.locationList.observe(viewLifecycleOwner, { data ->
            locationAdapter.submitList(data.documents.reversed())
        })
    }

    // init Toolbar
    private fun initToolbarWithNavigation() {
        val navController = findNavController()
        binding.tbMapToolbar.apply {
            inflateMenu(R.menu.menu_add)
            setupWithNavController(navController)
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menu_add -> {
                        if (mainViewModel.uploadLocationData.value == null) {
                            Toast.makeText(context, "장소를 선택해 주세요", Toast.LENGTH_LONG).show()
                            false
                        } else {
                            navController.navigate(R.id.action_mapFragment_to_uploadContentFragment)
                            true
                        }
                    }
                    else -> false
                }
            }
        }
    }

    // init MapView
    private fun initMapView() {
        mapView = MapView((activity as MainActivity))
        binding.flMapView.addView(mapView)
        mapView.removeAllPOIItems()
    }

    private fun drawMapPoint(locationData: LocationItem) {
        mapView.setMapCenterPoint(
            MapPoint.mapPointWithGeoCoord(
                locationData.y.toDouble(),
                locationData.x.toDouble()), true)

        val point = MapPOIItem().apply {
            itemName = locationData.place_name
            mapPoint = MapPoint.mapPointWithGeoCoord(
                locationData.y.toDouble(),
                locationData.x.toDouble()
            )
            markerType = MapPOIItem.MarkerType.BluePin
            selectedMarkerType = MapPOIItem.MarkerType.RedPin
        }

        mapView.addPOIItem(point)
    }

    private inner class LocationAdapter(val onClick: (LocationItem) -> Unit) :
        ListAdapter<LocationItem, LocationViewHolder>(LocationItem.DiffCallback) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_location_list, parent, false)
            return LocationViewHolder(view, onClick)
        }

        override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
            val contentItem = getItem(position)
            holder.rootView.tag = contentItem

            holder.tvPlaceName.text   = contentItem.place_name
            holder.tvAddress.text     = contentItem.address_name
            holder.tvRoadAddress.text = contentItem.road_address_name
        }
    }
}

private class LocationViewHolder(view: View, onClick: (LocationItem) -> Unit) :
    RecyclerView.ViewHolder(view) {
    val rootView = view
    val locationItemContainer: ConstraintLayout = view.findViewById(R.id.cl_locationItem)
    val tvPlaceName     : TextView = view.findViewById(R.id.tv_placeName)
    val tvAddress       : TextView = view.findViewById(R.id.tv_address)
    val tvRoadAddress   : TextView = view.findViewById(R.id.tv_roadAddress)

    init {
        locationItemContainer.setOnClickListener {
            val locationItem = rootView.tag as? LocationItem ?: return@setOnClickListener
            onClick(locationItem)
        }
    }
}