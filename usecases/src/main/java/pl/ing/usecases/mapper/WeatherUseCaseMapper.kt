package pl.ing.usecases.mapper

import pl.ing.commons.BaseMapper
import pl.ing.domain.WeatherRsp
import pl.ing.commons.Result
import pl.ing.commons.ResultType

/**
 * Created by adamnowicki on 2019-09-30.
 */
class WeatherUseCaseMapper {

    object WeatherRspToWeatherData : BaseMapper<Result<WeatherRsp>, Result<WeatherData>> {
        override fun map(type: Result<WeatherRsp>?): Result<WeatherData> {

            lateinit var result: Result<WeatherData>

            if (type?.resultType == ResultType.ERROR) {
                result = Result(ResultType.ERROR, error = type.error)

            } else {
                val weatherData = WeatherData(
                    temp = getCelsiusTemp(type?.data?.list?.first()?.main?.temp ?: KELVIN_ZERO),
                    windSpeed = type?.data?.list?.first()?.wind?.speed ?: 0.0,
                    city = type?.data?.city?.name ?: "",
                    weatherType = mapWeatherType(type?.data?.list?.first()?.weather?.first()?.icon)
                )
                result = Result(ResultType.SUCCESS, data = weatherData)
            }
            return result
        }
    }

    companion object {

        private val KELVIN_ZERO = 273.15

        private fun mapWeatherType(iconCode: String?): WeatherType {
            return when (iconCode) {
                "01d" -> WeatherType.SUNNY
                "03d" -> WeatherType.CLOUDY
                "09d" -> WeatherType.RAINY
                "13d" -> WeatherType.SNOWY
                else -> WeatherType.CLOUDY
            }
        }

        private fun getCelsiusTemp(kelvins: Double) : Double = kelvins - KELVIN_ZERO
    }
}
