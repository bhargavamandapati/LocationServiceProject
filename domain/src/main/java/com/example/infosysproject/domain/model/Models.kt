package com.example.infosysproject.domain.model

data class CarLocation(
    val latitude: Double,
    val longitude: Double
)

data class PointOfInterest(
    val id: String,
    val name: String,
    val location: CarLocation,
    val type: PoiType
)

data class HistoricalData(
    val timestamp: Long,
    val locationUsed: CarLocation,
    val presentedPois: List<PointOfInterest>
)