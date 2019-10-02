package pl.ing.usecases.mapper

/**
 * Created by adamnowicki on 2019-10-01.
 */
data class WeatherData (
    val temp: Double,
    val windSpeed: Double,
    val city: String,
    val weatherType: WeatherType
)