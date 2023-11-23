package eu.golovkov.core.network.di

import eu.golovkov.core.network.NBANetworkDataSource
import eu.golovkov.core.network.retrofit.RetrofitNBANetwork
import org.koin.core.module.Module
import org.koin.dsl.module

object NetworkModule {
    operator fun invoke(): Module = module {
        single<NBANetworkDataSource> {
            RetrofitNBANetwork()
        }
    }
}