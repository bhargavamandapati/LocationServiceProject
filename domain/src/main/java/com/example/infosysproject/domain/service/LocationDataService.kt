package com.example.infosysproject.domain.service

import com.example.infosysproject.domain.factory.FilterFactory
import com.example.infosysproject.domain.model.CarLocation
import com.example.infosysproject.domain.model.HistoricalData
import com.example.infosysproject.domain.model.PointOfInterest
import com.example.infosysproject.domain.repository.HistoryRepository
import com.example.infosysproject.domain.source.CarInfoProvider
import com.example.infosysproject.domain.source.PoiDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class LocationDataService(
    private val carInfoProvider: CarInfoProvider,
    private val poiDataSource: PoiDataSource,
    private val filterFactory: FilterFactory,
    private val historyRepository: HistoryRepository
) {

    /**
     * Performs a one-time fetch and filter of POIs based on the most recent car location.
     * This function is asynchronous because it needs to wait for the first location from the flow.
     */
    suspend fun getFilteredPoisForCurrentLocation(): List<PointOfInterest> {
        // Asynchronously get the first available location from the stream.
        val location = carInfoProvider.getCarLocationFlow().first()
        val model = carInfoProvider.getCarModel()

        val filter = filterFactory.getFilterForCar(model)

        // The PoiDataSource should likely take the location to fetch relevant POIs.
        // I'll assume fetchRawPois is a suspend function for now.
        val rawPois = poiDataSource.fetchRawPois(location)

        val filteredPois = filter.filter(rawPois, location)

        // Create and add a history entry for this filtering event.
        val historyEntry = HistoricalData(
            timestamp = System.currentTimeMillis(),
            locationUsed = location,
            presentedPois = filteredPois
        )
        historyRepository.addHistoricalEntry(historyEntry)

        return filteredPois
    }

    /**
     * Exposes the continuous stream of car location updates directly to the caller (e.g., the ViewModel).
     */
    fun getCarLocationFlow(): Flow<CarLocation> {
        return carInfoProvider.getCarLocationFlow()
    }

    fun getHistoricalData(): List<HistoricalData> {
        return historyRepository.getHistory()
    }
}