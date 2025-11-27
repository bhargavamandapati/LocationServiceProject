package com.example.infosysproject

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.infosysproject.domain.model.HistoricalData
import com.example.infosysproject.domain.model.PointOfInterest
import com.example.infosysproject.domain.service.LocationDataService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LocationDataViewModel(
    private val service: LocationDataService
) : ViewModel() {

    private val _pois = MutableLiveData<List<PointOfInterest>>()
    val pois: LiveData<List<PointOfInterest>> = _pois

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _history = MutableStateFlow<List<HistoricalData>>(emptyList())
    val history: StateFlow<List<HistoricalData>> = _history.asStateFlow()

    fun loadData() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                val pois = withContext(Dispatchers.Default) {
                    service.getFilteredPoisForCurrentLocation()
                }
                _pois.value = pois
                val historyData = withContext(Dispatchers.Default) {
                    service.getHistoricalData()
                }
                _history.value = historyData
            } catch (e: Exception) {
                _error.value = e.message ?: "Unexpected error"
            } finally {
                _loading.value = false
            }
        }
    }

    fun refreshHistory() {
        viewModelScope.launch {
            val historyData = withContext(Dispatchers.Default) {
                service.getHistoricalData()
            }
            _history.value = historyData
        }
    }

    class Factory(
        private val service: LocationDataService
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LocationDataViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LocationDataViewModel(service) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class ${modelClass.name}")
        }
    }
}
