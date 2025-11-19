package com.example.infosysproject

import com.example.infosysproject.domain.model.HistoricalData
import com.example.infosysproject.domain.model.PointOfInterest

interface LocationDataView {
    fun displayPointsOfInterest(pois: List<PointOfInterest>)
    fun displayHistory(history: List<HistoricalData>)
    fun showLoading(isLoading: Boolean)
    fun showError(message: String)
}