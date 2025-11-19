package com.example.infosysproject.domain.source

import com.example.infosysproject.domain.model.CarLocation

interface CarInfoProvider {
    fun getCurrentCarLocation(): CarLocation
    fun getCarModel(): String
}