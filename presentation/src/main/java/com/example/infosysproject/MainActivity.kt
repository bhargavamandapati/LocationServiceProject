package com.example.infosysproject

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.infosysproject.data.repository.InMemoryHistoryRepository
import com.example.infosysproject.data.source.MockCarInfoProvider
import com.example.infosysproject.data.source.MockPoiDataSource
import com.example.infosysproject.domain.factory.FilterFactory
import com.example.infosysproject.domain.filter.DefaultFilter
import com.example.infosysproject.domain.model.HistoricalData
import com.example.infosysproject.domain.model.PointOfInterest
import com.example.infosysproject.domain.service.LocationDataService
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), LocationDataView {

    private val service by lazy {
        val carInfo = MockCarInfoProvider()
        val poiSource = MockPoiDataSource()
        val history = InMemoryHistoryRepository()
        val filterFactory = FilterFactory(DefaultFilter())
        LocationDataService(carInfo, poiSource, filterFactory, history)
    }

    private val viewModel: LocationDataViewModel by viewModels {
        LocationDataViewModel.Factory(service)
    }

    private lateinit var progressBar: ProgressBar
    private lateinit var poiText: TextView
    private lateinit var historyText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progress)
        poiText = findViewById(R.id.poi_list)
        historyText = findViewById(R.id.history_list)

        viewModel.loading.observe(this) { isLoading ->
            showLoading(isLoading == true)
        }

        viewModel.pois.observe(this) { pois ->
            displayPointsOfInterest(pois.orEmpty())
        }

        viewModel.error.observe(this) { message ->
            message?.let { showError(it) }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.history.collect { history ->
                    displayHistory(history)
                }
            }
        }

        viewModel.loadData()
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
