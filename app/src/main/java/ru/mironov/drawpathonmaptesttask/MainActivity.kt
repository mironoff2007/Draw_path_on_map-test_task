package ru.mironov.drawpathonmaptesttask

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.mapview.MapView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.mironov.drawpathonmaptesttask.model.MainViewModel

class MainActivity : AppCompatActivity() {

    private var mapView: MapView? = null

    private lateinit var viewModel: MainViewModel

    private lateinit var map: com.yandex.mapkit.map.Map

    private lateinit var progressBar: ProgressBar
    private lateinit var mapObjects: MapObjectCollection
    private lateinit var textView: TextView
    private lateinit var updateButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MapKitFactory.setApiKey("2fdb0bd3-d231-46a6-ab8a-3a5668c377f7")
        MapKitFactory.initialize(this)

        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        mapView = findViewById<View>(R.id.mapview) as MapView
        textView = findViewById<View>(R.id.textView) as TextView
        progressBar = findViewById<View>(R.id.progressBar) as ProgressBar
        updateButton = findViewById<View>(R.id.updateButton) as Button

        map = mapView!!.map

        mapObjects = map.mapObjects.addCollection()

        setupObserver()

        viewModel.getGeoJson()
        updateButton.setOnClickListener { viewModel.getGeoJson() }
    }

    @SuppressLint("SetTextI18n")
    private fun setupObserver() {
        viewModel.viewModelStatus.observe(this) {
            when (it) {
                Status.RESPONSE -> {
                    //посчитать и показать длину всех линий
                    lifecycleScope.launch(Dispatchers.Default) {
                        val len = viewModel.calculateLengths()
                        runOnUiThread {
                            textView.text =
                                getString(R.string.length) + " = " + len.toString() + getString(R.string.length_unit)
                        }
                    }
                    //отобразить все полилинии
                    drawPolylines()
                    //отдельно отрисовать самую длинную полилинию
                    //drawPolyline(159)
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
                    textView.text = getString(R.string.loading)
                }
            }
        }
    }

    //Выделить одну полилилнию
    private fun drawPolyline(i: Int) {
        if (viewModel.arrayPolylines.size > i) {
            val mapPolyline = mapObjects.addPolyline(viewModel.arrayPolylines[i])
            mapPolyline.strokeColor = Color.RED
            mapPolyline.strokeWidth = 5F
        }
    }

    //нарисовать все полилинии
    private fun drawPolylines() {
        viewModel.arrayPolylines.forEach { polyline ->
            val mapPolyline = mapObjects.addPolyline(polyline)
            mapPolyline.strokeColor = Color.GREEN
            mapPolyline.strokeWidth = 1F
        }
    }

    //переместить камеру
    //можно найти карйние точки линий и отцентрировать камеру, но пока не стал этого делать
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
