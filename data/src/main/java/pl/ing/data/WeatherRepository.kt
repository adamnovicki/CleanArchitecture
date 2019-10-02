package pl.ing.data

import pl.ing.domain.WeatherRsp

/**
 * Created by adamnowicki on 2019-09-29.
 */
class WeatherRepository(private val dataSource: WeatherDataSource) {
    suspend fun getWeather(city: String) = dataSource.get(city)
}