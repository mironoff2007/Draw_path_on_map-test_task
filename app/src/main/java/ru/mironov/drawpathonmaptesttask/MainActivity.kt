package ru.mironov.drawpathonmaptesttask

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.geometry.Polyline
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.mapview.MapView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.mironov.drawpathonmaptesttask.model.MainViewModel
import ru.mironov.drawpathonmaptesttask.model.geojson.GeoJsonParser

class MainActivity : AppCompatActivity() {

    private var mapView: MapView? = null

    private lateinit var viewModel: MainViewModel

    private lateinit var map: com.yandex.mapkit.map.Map

    private lateinit var progressBar: ProgressBar
    private lateinit var mapObjects: MapObjectCollection
    private lateinit var textView: TextView
    private lateinit var updateButton: Button
    private lateinit var parseTypeSpinner: Spinner

    private var selectedParser: String = GeoJsonParser.Parser.GSON.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MapKitFactory.setApiKey("2fdb0bd3-d231-46a6-ab8a-3a5668c377f7")
        MapKitFactory.initialize(this)

        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        mapView = findViewById<View>(R.id.mapview) as MapView
        textView = findViewById<View>(R.id.textView) as TextView
        progressBar = findViewById<View>(R.id.progressBar) as ProgressBar
        updateButton = findViewById<View>(R.id.updateButton) as Button
        parseTypeSpinner = findViewById<View>(R.id.parseTypeSpinner) as Spinner

        val parsersNames = GeoJsonParser.Parser.values().map { it.name }

        parseTypeSpinner.adapter = CustomAdapter(this, parsersNames)
        parseTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                i: Int,
                l: Long
            ) {
                selectedParser = parsersNames[i]
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {

            }
        }

        map = mapView!!.map

        mapObjects = map.mapObjects.addCollection()

        setupObserver()

        viewModel.getGeoJson(selectedParser)
        updateButton.setOnClickListener { viewModel.getGeoJsonRes(this, selectedParser) }
    }

    @SuppressLint("SetTextI18n")
    private fun setupObserver() {
        viewModel.viewModelStatus.observe(this) {
            when (it) {
                is Status.RESPONSE -> {
                    //посчитать и показать длину всех линий
                    lifecycleScope.launch(Dispatchers.Default) {
                        val len = viewModel.calculateLengths()
                        runOnUiThread {
                            textView.text =
                                getString(R.string.length) + " = " + len.toString() + getString(R.string.length_unit)
                        }
                    }
                    //отобразить все полилинии
                    drawPolylines(it.lines)
                    //отдельно отрисовать самую длинную полилинию
                    //drawPolyline(159)
                    progressBar.visibility = View.INVISIBLE
                }
                is Status.ERROR -> {
                    progressBar.visibility = View.INVISIBLE
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is Status.LOADING -> {
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
    private fun drawPolylines(lines: List<Polyline>) {
        lines.forEach { polyline ->
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
