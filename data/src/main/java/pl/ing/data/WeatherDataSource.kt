package pl.ing.data

import pl.ing.commons.Result
import pl.ing.domain.WeatherRsp

/**
 * Created by adamnowicki on 2019-09-29.
 */
interface WeatherDataSource {
    suspend fun get(city: String): Result<WeatherRsp>
}