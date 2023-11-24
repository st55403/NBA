package eu.golovkov.feature.playerlist.di

import eu.golovkov.feature.playerlist.PlayerListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module


object PlayerListModule {
    operator fun invoke(): Module = module {
        viewModel {
            PlayerListViewModel(
                apiService = get(),
            )
        }
    }
}