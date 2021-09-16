package org.kjh.mypracticeprojects.ui.main.postdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import com.orhanobut.logger.Logger
import dagger.hilt.android.AndroidEntryPoint
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import org.kjh.mypracticeprojects.R
import org.kjh.mypracticeprojects.databinding.FragmentPostDetailInfoBinding
import org.kjh.mypracticeprojects.model.PostModel
import org.kjh.mypracticeprojects.navigate
import org.kjh.mypracticeprojects.ui.base.BaseFragment
import org.kjh.mypracticeprojects.util.DataState

@AndroidEntryPoint
class PostDetailInfoFragment
    : BaseFragment<FragmentPostDetailInfoBinding>(R.layout.fragment_post_detail_info) {

    private val viewModel: PostDetailViewModel by activityViewModels()

    private lateinit var mapView: MapView
    private lateinit var postData: PostModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.postListByPlace.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    postData = dataState.data!![0]
                    binding.postModel = postData
                    drawMapPoint(postData)
                }
            }
        })

        initMapView()

        binding.btnShowBigMap.setOnClickListener {
            binding.flPostDetailMapView.removeView(mapView)
            navigate(
                action = R.id.action_postDetailFragment_to_mapInfoFragment,
                bundle = bundleOf("postItem" to postData)
            )
        }
    }

    private fun initMapView() {
        mapView = MapView(activity)
        binding.flPostDetailMapView.addView(mapView)
        mapView.removeAllPOIItems()
    }

    private fun drawMapPoint(locationData: PostModel) {
        mapView.setMapCenterPoint(
            MapPoint.mapPointWithGeoCoord(
                locationData.y.toDouble(),
                locationData.x.toDouble()), true)

        val point = MapPOIItem().apply {
            itemName = locationData.placeName
            mapPoint = MapPoint.mapPointWithGeoCoord(
                locationData.y.toDouble(),
                locationData.x.toDouble()
            )
            markerType = MapPOIItem.MarkerType.BluePin
            selectedMarkerType = MapPOIItem.MarkerType.RedPin
        }

        mapView.addPOIItem(point)
    }

    companion object {
        @JvmStatic
        fun newInstance() = PostDetailInfoFragment()
    }
}