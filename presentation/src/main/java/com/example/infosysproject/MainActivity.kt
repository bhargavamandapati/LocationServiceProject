package com.example.infosysproject

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), LocationDataView {

    private val service by lazy {
        val carInfo = MockCarInfoProvider()
        val poiSource = MockPoiDataSource()
        val history = InMemoryHistoryRepository()
        val filterFactory = FilterFactory(DefaultFilter())
        LocationDataService(carInfo, poiSource, filterFactory, history)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progress)
        poiText = findViewById(R.id.poi_list)
        historyText = findViewById(R.id.history_list)

        showLoading(true)
        val pois = service.getFilteredPoisForCurrentLocation()
        val history = service.getHistoricalData()
        showLoading(false)

        displayPointsOfInterest(pois)
        displayHistory(service.getHistoricalData())
    }

    override fun displayPointsOfInterest(pois: List<PointOfInterest>) {
        poiText.text = pois.joinToString("\n") { "${it.name} (${it.type}) @ ${it.location.latitude},${it.location.longitude}" }
    }

    override fun displayHistory(history: List<HistoricalData>) {
        historyText.text = history.joinToString("\n") { entry ->
            val names = entry.presentedPois.joinToString { it.name }
            "${entry.timestamp}: $names"
        }
    }

    override fun showLoading(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}