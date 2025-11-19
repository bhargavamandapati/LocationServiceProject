package com.example.infosysproject.domain.repository

import com.example.infosysproject.domain.model.HistoricalData

interface HistoryRepository {
    fun addHistoricalEntry(entry: HistoricalData)
    fun getHistory(): List<HistoricalData>
}