package com.example.infosysproject.domain.filter

import com.example.infosysproject.domain.model.CarLocation
import com.example.infosysproject.domain.model.PoiType
import com.example.infosysproject.domain.model.PointOfInterest

class Polestar2Filter : LocationDataFilter {
    override fun filter(
        pois: List<PointOfInterest>,
        currentLocation: CarLocation
    ): List<PointOfInterest> {
        println("Using Polestar 2 Filter: Prioritizing Charging Stations.")
        return pois.filter {
            it.type == PoiType.CHARGING_STATION
        }
    }
}