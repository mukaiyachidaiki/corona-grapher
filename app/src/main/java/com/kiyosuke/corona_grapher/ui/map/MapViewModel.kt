package com.kiyosuke.corona_grapher.ui.map

import androidx.lifecycle.*
import com.google.android.gms.maps.model.Marker
import com.kiyosuke.corona_grapher.data.repository.CoronavirusRepository
import com.kiyosuke.corona_grapher.model.Location
import kotlinx.coroutines.launch
import timber.log.Timber

class MapViewModel(
    private val repo: CoronavirusRepository
) : ViewModel() {

    val locations = repo.locations().asLiveData(viewModelScope.coroutineContext)

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> get() = _message

    fun refresh() {
        viewModelScope.launch {
            try {
                repo.refreshLocations()
            } catch (e: Exception) {
                // TODO: エラーメッセージを生成
                Timber.e(e)
            }
        }
    }

    fun onClickedMarker(marker: Marker) {
        val location = marker.tag as? Location ?: return
        viewModelScope.launch {
            try {
                val locationDetail = repo.location(location.id)
            } catch (e: Exception) {
                // TODO: エラーメッセージを生成
                Timber.e(e)
            }
        }
    }
}