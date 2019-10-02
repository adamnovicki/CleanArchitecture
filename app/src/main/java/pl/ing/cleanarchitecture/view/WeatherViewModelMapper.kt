package pl.ing.cleanarchitecture.view

import pl.ing.commons.BaseMapper
import pl.ing.usecases.mapper.WeatherData
import pl.ing.usecases.mapper.WeatherType
import kotlin.math.roundToLong

/**
 * Created by adamnowicki on 2019-09-30.
 */
class WeatherViewModelMapper {

    object WeatherToUI : BaseMapper<WeatherData, WeatherUI> {
        override fun map(type: WeatherData?): WeatherUI {
            return WeatherUI(
                temp = (type?.temp ?: 0.0).roundToLong(),
                windSpeed = (type?.windSpeed ?: 0.0).roundToLong(),
                city = type?.city ?: "",
                iconType = mapWeatherType(type?.weatherType)
            )
        }
    }

    companion object {

        private fun mapWeatherType(weatherType: WeatherType?): IconType {
            return when (weatherType) {
                WeatherType.SUNNY -> IconType.SUN
                WeatherType.CLOUDY -> IconType.CLOUD
                WeatherType.RAINY -> IconType.RAIN
                WeatherType.SNOWY -> IconType.SNOW
                else -> IconType.CLOUD
            }
        }
    }
}
