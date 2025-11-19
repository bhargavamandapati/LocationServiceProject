package com.example.infosysproject.domain.filter

import com.example.infosysproject.domain.model.CarLocation
import com.example.infosysproject.domain.model.PointOfInterest

class DefaultFilter : LocationDataFilter {
    override fun filter(
        pois: List<PointOfInterest>,
        currentLocation: CarLocation
    ): List<PointOfInterest> {
        println("Using Default Filter: No filtering.")
        return pois
    }
}