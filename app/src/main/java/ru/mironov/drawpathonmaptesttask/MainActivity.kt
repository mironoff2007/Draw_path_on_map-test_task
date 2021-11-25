package ru.mironov.drawpathonmaptesttask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.map.CameraPosition

import android.view.View
import com.yandex.mapkit.Animation

import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.geometry.Polyline

class MainActivity : AppCompatActivity() {

    private var mapView: MapView? = null

    private lateinit var viewModel: MainViewModel

    private lateinit var map:com.yandex.mapkit.map.Map

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MapKitFactory.setApiKey("2fdb0bd3-d231-46a6-ab8a-3a5668c377f7")
        MapKitFactory.initialize(this)

        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        mapView = findViewById<View>(R.id.mapview) as MapView

        map=mapView!!.map

        setupObserver()

        setupMapsCamera()

        viewModel.getGeoJson()
    }

    private fun setupObserver() {
        viewModel.viewModelStatus.observe(this) {
            when (it) {
                Status.RESPONSE -> {

                }
                Status.ERROR -> {
                    Toast.makeText(applicationContext, "ошибка", Toast.LENGTH_SHORT).show()
                }
                Status.LOADING -> {
                    //progressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setupMapsCamera(){

        map.move(
            CameraPosition( Point(
                59.30014082100015,
                81.3642031920001
            ), 11.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 0F),
            null
        )

        var polyline =  Polyline( arrayListOf(
            Point(
                59.30014082100015,
                81.3642031920001
            ),
            Point(
                59.35694420700011,
                81.34430573100003
            ),
            Point(
                59.38542728000014,
                81.3305524760001
            ),
            Point(
                59.34245853000007,
                81.30426666900009
            ),
            Point(
                59.285166863000114,
                81.30003489799999
            ),
            Point(
                59.22486412900017,
                81.30390045800007
            )))
        // Добавляем линию на карту.
        map.mapObjects.addCollection().addPolyline(polyline);
        // Устанавливаем карте границы линии.
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
