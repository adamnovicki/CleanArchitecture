package pl.ing.usecases

import pl.ing.data.WeatherRepository
import pl.ing.domain.WeatherRsp
import pl.ing.commons.Result
import pl.ing.commons.ResultType
import pl.ing.usecases.mapper.WeatherData
import pl.ing.usecases.mapper.WeatherUseCaseMapper

/**
 * Created by adamnowicki on 2019-09-29.
 */
class GetWeatherUseCase(private val weatherRepository: WeatherRepository) {

    suspend operator fun invoke(city: String) : Result<WeatherData> {
        val resultRsp = weatherRepository.getWeather(city)
        return WeatherUseCaseMapper.WeatherRspToWeatherData.map(resultRsp)
    }
}