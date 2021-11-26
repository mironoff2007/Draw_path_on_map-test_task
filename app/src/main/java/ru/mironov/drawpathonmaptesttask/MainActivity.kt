package ru.mironov.drawpathonmaptesttask

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView

class MainActivity : AppCompatActivity() {

    private var mapView: MapView? = null

    private lateinit var viewModel: MainViewModel

    private lateinit var map: com.yandex.mapkit.map.Map

    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MapKitFactory.setApiKey("2fdb0bd3-d231-46a6-ab8a-3a5668c377f7")
        MapKitFactory.initialize(this)

        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        mapView = findViewById<View>(R.id.mapview) as MapView

        progressBar = findViewById<View>(R.id.progressBar) as ProgressBar

        map = mapView!!.map

        setupObserver()

        viewModel.getGeoJson()
    }

    private fun setupObserver() {
        viewModel.viewModelStatus.observe(this) {
            when (it) {
                Status.RESPONSE -> {
                    drawPolylines()
                    progressBar.visibility = View.INVISIBLE
                }
                Status.ERROR -> {
                    progressBar.visibility = View.INVISIBLE
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    fun drawPolylines() {
        viewModel.arrayPolylines.forEach { polyline ->
            map.mapObjects.addCollection().addPolyline(polyline)
        }
    }

    fun moveCamera() {
        map.move(
            CameraPosition(Point(50.0, 50.0), 2.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 0F),
            null
        )
    }

    override fun onStop() {
        // onStop calls should be passed to MapView and MapKit instances.
        mapView!!.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onStart() {
        // onStart calls should be passed to MapView and MapKit instances.
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView!!.onStart()
    }
}
