package org.kjh.mypracticeprojects.ui.main.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
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
import org.kjh.mypracticeprojects.ui.base.BaseFragment
import org.kjh.mypracticeprojects.ui.main.MainActivity
import org.kjh.mypracticeprojects.ui.main.MainViewModel

@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding>(R.layout.fragment_map) {

    private val viewModel: MapViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var mapView: MapView
    private lateinit var locationAdapter: LocationListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        initToolbarWithNavigation()
        initMapView()
        initLocationSearchResultRecyclerView()
        subscribeObserver()
    }

    private fun subscribeObserver() {
        viewModel.locationList.observe(viewLifecycleOwner, { data ->
            locationAdapter.submitList(data.documents)
        })
    }

    private fun initLocationSearchResultRecyclerView() {
        locationAdapter = LocationListAdapter {
            mainViewModel.setUploadLocationData(it)
            drawMapPoint(it)
        }

        binding.rvLocationList.apply {
            adapter = locationAdapter
        }
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
                            Toast.makeText(context, getString(R.string.should_select_location), Toast.LENGTH_LONG).show()
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
}

