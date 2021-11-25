package ru.mironov.drawpathonmaptesttask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

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
}
