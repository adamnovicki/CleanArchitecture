package pl.ing.cleanarchitecture

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pl.ing.data.WeatherDataSource
import pl.ing.domain.WeatherRsp
import pl.nowicki.openweatherdexprotector.WeatherApiService
import pl.ing.commons.Result
import pl.ing.commons.exception.CancelledFetchDataException
import pl.ing.commons.exception.NetworkException
import timber.log.Timber

/**
 * Created by adamnowicki on 2019-09-29.
 */
class WeatherApiDataSource(private val weatherApiService: WeatherApiService) : WeatherDataSource {
    
    override suspend fun get(city: String): Result<WeatherRsp> {

        var result: Result<WeatherRsp> = Result.success(WeatherRsp())

        withContext(Dispatchers.IO) {

            try {
                val request = weatherApiService.forecast(city)

                val response: WeatherRsp = request.await()
                Timber.d("onWeatherReceived ${response}")

                request.let {
                    if (it.isCompleted) {
                        result = Result.success(response)
                    }
                    else if (it.isCancelled) {
                        result = Result.error(CancelledFetchDataException())
                    }
                }
            } catch (ex: Exception) {
                result = Result.error(NetworkException())
                Timber.d("onWeatherReceived NetworkException")
            }
        }
        return result
    }
}