package org.kjh.mypracticeprojects.ui.main

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import org.kjh.mypracticeprojects.R
import org.kjh.mypracticeprojects.databinding.FragmentMapInfoBinding
import org.kjh.mypracticeprojects.model.PostModel
import org.kjh.mypracticeprojects.ui.base.BaseFragment
import org.kjh.mypracticeprojects.ui.main.post.PostDetailFragment.Companion.DIALOG_X_Y_INFO

/**
 * MyPracticeProjects
 * Class: MapDialogFragment
 * Created by mac on 2021/08/30.
 *
 * Description:
 */
class MapInfoFragment: BaseFragment<FragmentMapInfoBinding>(R.layout.fragment_map_info),
MapView.CurrentLocationEventListener, MapView.MapViewEventListener {

    private lateinit var mapView: MapView
    private lateinit var postData: PostModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postData = arguments?.get(DIALOG_X_Y_INFO) as PostModel

        initToolbarWithNavigation()
        initMapViewWithLocation()

        binding.btnMyLocation.setOnClickListener {
            mapView.currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading
        }

        binding.btnTargetLocation.setOnClickListener {
            mapView.setMapCenterPoint(
                MapPoint.mapPointWithGeoCoord(
                    postData.y.toDouble(),
                    postData.x.toDouble()
                ), true)
        }
    }



    private fun initToolbarWithNavigation() {
        binding.tbMapInfo.setupWithNavController(findNavController())
    }

    private fun initMapViewWithLocation(){
        mapView = MapView(activity as MainActivity).apply {
            setMapViewEventListener(this@MapInfoFragment)
        }

        binding.flMapView.addView(mapView)
        mapView.removeAllPOIItems()

        val xValue = postData.x.toDouble()
        val yValue = postData.y.toDouble()

        mapView.setMapCenterPoint(
            MapPoint.mapPointWithGeoCoord(
                yValue,
                xValue
            ), true
        )

        val point = MapPOIItem().apply {
            itemName = postData.place_name
            mapPoint = MapPoint.mapPointWithGeoCoord(
                yValue,
                xValue
            )
            markerType = MapPOIItem.MarkerType.BluePin
            selectedMarkerType = MapPOIItem.MarkerType.RedPin
        }

        mapView.addPOIItem(point)
    }

    override fun onCurrentLocationUpdate(p0: MapView?, p1: MapPoint?, p2: Float) {}
    override fun onCurrentLocationDeviceHeadingUpdate(p0: MapView?, p1: Float) {}
    override fun onCurrentLocationUpdateFailed(p0: MapView?) {}
    override fun onCurrentLocationUpdateCancelled(p0: MapView?) {}


    override fun onMapViewInitialized(p0: MapView?) {}
    override fun onMapViewCenterPointMoved(p0: MapView?, p1: MapPoint?) {}
    override fun onMapViewZoomLevelChanged(p0: MapView?, p1: Int) {}
    override fun onMapViewSingleTapped(p0: MapView?, p1: MapPoint?) {}
    override fun onMapViewDoubleTapped(p0: MapView?, p1: MapPoint?) {}
    override fun onMapViewLongPressed(p0: MapView?, p1: MapPoint?) {}
    override fun onMapViewDragStarted(p0: MapView?, p1: MapPoint?) {}
    override fun onMapViewDragEnded(p0: MapView?, p1: MapPoint?) {}
    override fun onMapViewMoveFinished(p0: MapView?, p1: MapPoint?) {
        p0?.let {
            if (it.currentLocationTrackingMode != MapView.CurrentLocationTrackingMode.TrackingModeOff) {
                it.currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOff
            }
        }
    }
}