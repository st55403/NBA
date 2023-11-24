package eu.golovkov.feature.playerdetails.di

import eu.golovkov.feature.playerdetails.PlayerDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object PlayerDetailsModule {
    operator fun invoke(): Module = module {
        viewModel {
            PlayerDetailsViewModel(
                apiService = get(),
            )
        }
    }
}