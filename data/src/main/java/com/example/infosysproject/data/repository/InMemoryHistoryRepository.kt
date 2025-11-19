package com.example.infosysproject.data.repository

import com.example.infosysproject.domain.model.HistoricalData
import com.example.infosysproject.domain.repository.HistoryRepository

class InMemoryHistoryRepository : HistoryRepository {
    private val historyLog = mutableListOf<HistoricalData>()

    override fun addHistoricalEntry(entry: HistoricalData) {
        historyLog.add(entry)
    }

    override fun getHistory(): List<HistoricalData> {
        return historyLog.toList()
    }
}