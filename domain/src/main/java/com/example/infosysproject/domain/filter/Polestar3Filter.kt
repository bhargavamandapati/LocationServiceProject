package com.example.infosysproject.domain.filter

import com.example.infosysproject.domain.model.CarLocation
import com.example.infosysproject.domain.model.PointOfInterest

class Polestar3Filter : LocationDataFilter {
    override fun filter(
        pois: List<PointOfInterest>,
        currentLocation: CarLocation
    ): List<PointOfInterest> {
        println("Using Polestar 3 Filter: Prioritizing nearby POIs.")
        return pois.filter {
            it.id == "1" || it.id == "2"
        }
    }
}