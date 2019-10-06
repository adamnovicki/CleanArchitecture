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
                result = Result.error(error = type.error)

            } else {
                val details = type?.data?.list?.first()
                val weatherData = WeatherData(
                    temp = getCelsiusTemp(details?.main?.temp ?: KELVIN_ZERO),
                    windSpeed = details?.wind?.speed ?: 0.0,
                    city = type?.data?.city?.name ?: "",
                    weatherType = mapWeatherType(details?.weather?.first()?.id)
                )
                result = Result.success(data = weatherData)
            }
            return result
        }
    }

    companion object {

        private val KELVIN_ZERO = 273.15

        private fun mapWeatherType(id: Int?): WeatherType {
            return when (id) {
                800 -> WeatherType.SUNNY
                in 801..804 -> WeatherType.CLOUDY
                in 500..531 -> WeatherType.RAINY
                in 600..622 -> WeatherType.SNOWY
                else -> WeatherType.CLOUDY
            }
        }

        private fun getCelsiusTemp(kelvins: Double) : Double = kelvins - KELVIN_ZERO
    }
}
