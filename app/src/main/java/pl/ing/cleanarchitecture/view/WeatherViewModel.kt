package pl.ing.cleanarchitecture.view
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.ing.commons.Result
import pl.ing.commons.ResultType
import pl.ing.domain.WeatherRsp

import pl.ing.usecases.GetWeatherUseCase
import pl.ing.usecases.mapper.WeatherData
import timber.log.Timber

/**
 * Created by adamnowicki on 2019-09-30.
 */
class WeatherViewModel(private val getWeatherUseCase: GetWeatherUseCase) : ViewModel() {

    val weatherLiveData: MutableLiveData<WeatherUI> = MutableLiveData()
    val isErrorLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun getWeather(city: String) {
        Timber.d("getWeather")
        viewModelScope.launch {
            updateWeatherLiveData(getWeatherUseCase.invoke(city))
        }
    }

    private fun updateWeatherLiveData(result: Result<WeatherData>) {
        if (isResultSuccess(result.resultType)) {
            onResultSuccess(result.data)
        } else {
            onResultError()
        }
    }

    private fun isResultSuccess(resultType: ResultType): Boolean {
        return resultType == ResultType.SUCCESS
    }

    private fun onResultSuccess(weatherData: WeatherData?) {
        val weather = WeatherViewModelMapper.WeatherToUI.map(weatherData)
        Timber.d("onResultSuccess")
        weatherLiveData.postValue(weather)
    }

    private fun onResultError() {
        Timber.d("onResultError")
        isErrorLiveData.postValue(true)
    }
}