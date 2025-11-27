package com.example.infosysproject.domain.source


import com.example.infosysproject.domain.model.CarLocation
import kotlinx.coroutines.flow.Flow

interface CarInfoProvider {
    fun getCarLocationFlow(): Flow<CarLocation>

    fun getCarModel(): String
}