package com.example.infosysproject.data.source

import com.example.infosysproject.domain.model.CarLocation
import com.example.infosysproject.domain.model.PoiType
import com.example.infosysproject.domain.model.PointOfInterest
import com.example.infosysproject.domain.source.PoiDataSource

class MockPoiDataSource : PoiDataSource {
    override fun fetchRawPois(location: CarLocation): List<PointOfInterest> {
        return listOf(
            PointOfInterest("1", "Hello Restaurant", CarLocation(59.32, 18.06), PoiType.RESTAURANT),
            PointOfInterest("2", "Burger King", CarLocation(59.33, 18.07), PoiType.RESTAURANT),
            PointOfInterest("3", "Charging Station", CarLocation(59.31, 18.05), PoiType.CHARGING_STATION),
            PointOfInterest("4", "Indian Restaurant", CarLocation(60.0, 19.0), PoiType.RESTAURANT)
        )
    }
}