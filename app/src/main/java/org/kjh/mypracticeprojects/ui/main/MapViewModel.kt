package org.kjh.mypracticeprojects.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.kjh.mypracticeprojects.model.LocationItem
import org.kjh.mypracticeprojects.model.LocationResponse
import org.kjh.mypracticeprojects.repository.UserRepository
import org.kjh.mypracticeprojects.util.DataState
import javax.inject.Inject

/**
 * MyPracticeProjects
 * Class: MapViewModel
 * Created by mac on 2021/08/17.
 *
 * Description:
 */

@HiltViewModel
class MapViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    val inputLocation : MutableLiveData<String> = MutableLiveData()

    private val _locationList: MutableLiveData<LocationResponse> = MutableLiveData()
    val locationList: LiveData<LocationResponse> = _locationList

    private val _selectedLocation: MutableLiveData<LocationItem?> = MutableLiveData()
    val selectedLocation: LiveData<LocationItem?> = _selectedLocation

    fun searchLocation() {
        viewModelScope.launch {
            userRepository.searchLocation(inputLocation.value.toString())
                .onEach { dataState ->
                    when (dataState) {
                        is DataState.Success -> _locationList.value = dataState.data
                    }
                }
                .launchIn(viewModelScope)
        }
    }

    fun setSelectedLocation(item: LocationItem) {
        _selectedLocation.value = item
    }
}