package com.example.infosysproject.domain.factory

import com.example.infosysproject.domain.filter.DefaultFilter
import com.example.infosysproject.domain.filter.LocationDataFilter
import com.example.infosysproject.domain.filter.Polestar2Filter
import com.example.infosysproject.domain.filter.Polestar3Filter

class FilterFactory(private val defaultFilter: DefaultFilter) {

    fun getFilterForCar(carModel: String): LocationDataFilter {
        return when (carModel) {
            "Polestar 2" -> Polestar2Filter()
            "Polestar 3" -> Polestar3Filter()
            else -> defaultFilter
        }
    }
}