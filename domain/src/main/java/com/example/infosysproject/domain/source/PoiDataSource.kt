package com.example.infosysproject.domain.source

import com.example.infosysproject.domain.model.CarLocation
import com.example.infosysproject.domain.model.PointOfInterest

interface PoiDataSource {
    fun fetchRawPois(location: CarLocation): List<PointOfInterest>
}