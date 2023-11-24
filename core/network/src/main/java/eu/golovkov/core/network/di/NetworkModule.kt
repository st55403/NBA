package eu.golovkov.core.network.di

import eu.golovkov.core.network.RetrofitNBANetworkApi
import eu.golovkov.core.network.retrofit.RetrofitNBANetwork
import org.koin.core.module.Module
import org.koin.dsl.module

private const val BASE_URL = "https://www.balldontlie.io/api/v1/"

object NetworkModule {
    operator fun invoke(): Module = module {
        single<RetrofitNBANetworkApi> {
            RetrofitNBANetwork(BASE_URL)
        }
    }
}