package com.example.infosysproject.domain.service

import com.example.infosysproject.domain.factory.FilterFactory
import com.example.infosysproject.domain.model.HistoricalData
import com.example.infosysproject.domain.model.PointOfInterest
import com.example.infosysproject.domain.repository.HistoryRepository
import com.example.infosysproject.domain.source.CarInfoProvider
import com.example.infosysproject.domain.source.PoiDataSource

class LocationDataService(
    private val carInfoProvider: CarInfoProvider,
    private val poiDataSource: PoiDataSource,
    private val filterFactory: FilterFactory,
    private val historyRepository: HistoryRepository
) {

    fun getFilteredPoisForCurrentLocation(): List<PointOfInterest> {
        val location = carInfoProvider.getCurrentCarLocation()
        val model = carInfoProvider.getCarModel()

        val filter = filterFactory.getFilterForCar(model)

        val rawPois = poiDataSource.fetchRawPois(location)

        val filteredPois = filter.filter(rawPois, location)

        val historyEntry = HistoricalData(
            timestamp = System.currentTimeMillis(),
            locationUsed = location,
            presentedPois = filteredPois
        )
        historyRepository.addHistoricalEntry(historyEntry)

        return filteredPois
    }

    fun getHistoricalData(): List<HistoricalData> {
        return historyRepository.getHistory()
    }
}