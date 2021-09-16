package org.kjh.mypracticeprojects.ui.main

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.orhanobut.logger.Logger
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import org.kjh.mypracticeprojects.R
import org.kjh.mypracticeprojects.databinding.FragmentMapInfoBinding
import org.kjh.mypracticeprojects.model.PostModel
import org.kjh.mypracticeprojects.ui.base.BaseFragment

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
        postData = arguments?.get("postItem") as PostModel
        binding.postModel = postData

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

        binding.btnAddressCopy.setOnClickListener {
            val clipboard =
                (activity as MainActivity).getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            clipboard.setPrimaryClip(ClipData.newPlainText("주소", postData.address_name))

            Toast.makeText(context, "주소가 복사되었습니다.", Toast.LENGTH_LONG).show()
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
            itemName = postData.placeName
            mapPoint = MapPoint.mapPointWithGeoCoord(
                yValue,
                xValue
            )
            markerType = MapPOIItem.MarkerType.BluePin
            selectedMarkerType = MapPOIItem.MarkerType.RedPin
        }

        mapView.addPOIItem(point)
    }

    override fun onPause() {
        super.onPause()
        binding.flMapView.removeView(mapView)
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