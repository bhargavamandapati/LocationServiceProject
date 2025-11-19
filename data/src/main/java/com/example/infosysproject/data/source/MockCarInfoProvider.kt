package com.example.infosysproject.data.source

import com.example.infosysproject.domain.model.CarLocation
import com.example.infosysproject.domain.source.CarInfoProvider

class MockCarInfoProvider : CarInfoProvider {
    override fun getCurrentCarLocation(): CarLocation {
        return CarLocation(59.3293, 18.0686)
    }

    override fun getCarModel(): String {
        return "Polestar 2"
    }
}