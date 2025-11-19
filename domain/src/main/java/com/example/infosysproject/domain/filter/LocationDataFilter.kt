package com.example.infosysproject.domain.filter

import com.example.infosysproject.domain.model.CarLocation
import com.example.infosysproject.domain.model.PointOfInterest

interface LocationDataFilter {
    fun filter(
        pois: List<PointOfInterest>,
        currentLocation: CarLocation
    ): List<PointOfInterest>
}