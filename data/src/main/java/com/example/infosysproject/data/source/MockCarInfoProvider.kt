package com.example.infosysproject.data.source

import com.example.infosysproject.domain.model.CarLocation
import com.example.infosysproject.domain.source.CarInfoProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

class MockCarInfoProvider : CarInfoProvider {

    override fun getCarLocationFlow(): Flow<CarLocation> = flow {
        val baseLatitude = 59.3293
        val baseLongitude = 18.0686

        while (true) {
            val latOffset = Random.nextDouble(-0.0005, 0.0005)
            val lonOffset = Random.nextDouble(-0.0005, 0.0005)

            val newLocation = CarLocation(
                latitude = baseLatitude + latOffset,
                longitude = baseLongitude + lonOffset
            )

            emit(newLocation)
            delay(1000)
        }
    }

    override fun getCarModel(): String {
        return "Polestar 2"
    }
}