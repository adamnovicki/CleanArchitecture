package pl.ing.cleanarchitecture.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.ing.cleanarchitecture.R
import timber.log.Timber

class WeatherActivity : AppCompatActivity() {

    private val viewModel: WeatherViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        observeLiveData()
        getWeather()
    }

    private fun observeLiveData() {
        viewModel.isErrorLiveData.observe(this, Observer { onErrorReceived() })
        viewModel.weatherLiveData.observe(this, Observer(::onWeatherReceived))
    }

    private fun getWeather() {
        viewModel.getWeather("Katowice")
    }

    private fun onErrorReceived() {
        AlertDialog.Builder(this)
            .setTitle(R.string.error_title)
            .setCancelable(false)
            .setNegativeButton(R.string.error_cancel) { _, _ ->
                finish()
            }
            .setPositiveButton(R.string.error_try_again) { _, _ ->
                getWeather()
            }.show()
    }

    private fun onWeatherReceived(weather: WeatherUI) {
        cityTv.text = weather.city
        tempTv.text = getString(R.string.temp, weather.temp)
        windTv.text = getString(R.string.wind, weather.windSpeed)
    }
}
