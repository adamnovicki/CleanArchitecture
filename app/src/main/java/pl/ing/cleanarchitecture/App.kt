package pl.ing.cleanarchitecture

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module
import pl.ing.cleanarchitecture.network.WeatherApi
import pl.ing.cleanarchitecture.view.WeatherViewModel
import timber.log.Timber
import org.koin.androidx.viewmodel.dsl.viewModel
import pl.ing.data.WeatherRepository
import pl.ing.usecases.GetWeatherUseCase
import pl.nowicki.openweatherdexprotector.WeatherApiService

/**
 * Created by adamnowicki on 2019-09-30.
 */
class App : Application() {

    var listofModules =
        listOf(
            MainModule.mainModule
        )

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listofModules)

        }
    }
}