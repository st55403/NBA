package eu.golovkov.feature.teamdetails.di

import eu.golovkov.feature.teamdetails.TeamDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object TeamDetailsModule {
    operator fun invoke(): Module = module {
        viewModel {
            TeamDetailsViewModel(
                apiService = get(),
            )
        }
    }
}