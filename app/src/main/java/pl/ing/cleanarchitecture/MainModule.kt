package pl.ing.cleanarchitecture

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pl.ing.cleanarchitecture.network.WeatherApi
import pl.ing.cleanarchitecture.view.WeatherViewModel
import pl.ing.data.WeatherDataSource
import pl.ing.data.WeatherRepository
import pl.ing.usecases.GetWeatherUseCase
import pl.nowicki.openweatherdexprotector.WeatherApiService

object MainModule {

    val mainModule = module {
        single { WeatherApi(androidContext()) }
        single { provideApiService(get()) }
        single { WeatherApiDataSource(weatherApiService = get()) as WeatherDataSource}
        single { WeatherRepository(dataSource = get()) }
        single { GetWeatherUseCase(weatherRepository = get()) }
        viewModel { WeatherViewModel(getWeatherUseCase = get()) }
    }

    private fun provideApiService(api: WeatherApi): WeatherApiService {
        return api.getWeatherApiService()
    }
}