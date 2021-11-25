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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MapKitFactory.setApiKey("2fdb0bd3-d231-46a6-ab8a-3a5668c377f7")
        MapKitFactory.initialize(this)

        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        setupObserver()

        setupMapsCamera()
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
        mapView = findViewById<View>(R.id.mapview) as MapView
        val map=mapView!!.map
        map.move(
            CameraPosition(Point(-80.0, 60.0), 11.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 0F),
            null
        )


        // Создадим ломаную.
        var polyline =  Polyline( arrayListOf(Point(-80.0, 60.0), Point(-90.0, 50.0)))
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
