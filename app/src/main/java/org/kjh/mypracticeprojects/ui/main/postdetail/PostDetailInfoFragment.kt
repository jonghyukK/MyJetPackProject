package org.kjh.mypracticeprojects.ui.main.postdetail

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import org.kjh.mypracticeprojects.R
import org.kjh.mypracticeprojects.databinding.FragmentPostDetailInfoBinding
import org.kjh.mypracticeprojects.model.PostModel
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

        initMapView()
        subscribeObserver()

        binding.btnShowBigMap.setOnClickListener {
            binding.flPostDetailMapView.removeView(mapView)
            findNavController().navigate(
                resId = R.id.action_postDetailFragment_to_mapInfoFragment,
                args  = bundleOf("postItem" to postData)
            )
        }
    }

    private fun subscribeObserver() {
        viewModel.postListByPlace.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    postData = dataState.data!![0]
                    binding.postModel = postData
                    drawMapPoint(postData)
                }
            }
        })
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