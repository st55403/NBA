package eu.golovkov.feature.teamdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import eu.golovkov.core.designsystem.component.NBAOverlayLoadingWheel
import eu.golovkov.core.designsystem.theme.NBAPadding
import eu.golovkov.core.designsystem.theme.NBATheme
import eu.golovkov.core.model.data.Team
import eu.golovkov.core.ui.DevicePreviews
import eu.golovkov.core.ui.ErrorState
import eu.golovkov.core.ui.MockData
import eu.golovkov.core.ui.UiState
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@RootNavGraph(start = true)
@Destination
@Composable
fun TeamDetailsScreen(
    teamId: Int? = null,
    navigator: DestinationsNavigator,
) {
    val viewModel = getViewModel<TeamDetailsViewModel>()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(teamId) {
        teamId?.let { viewModel.getTeamDetails(it) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.team_details_title),
                        style = MaterialTheme.typography.headlineSmall,
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navigator.popBackStack() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back_button_content_desc),
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        TeamDetails(
            modifier = Modifier.padding(paddingValues),
            state = state,
        )
    }
}

@Composable
private fun TeamDetails(
    modifier: Modifier = Modifier,
    state: UiState<Team>,
) {
    when (state) {
        UiState.Error -> ErrorState(modifier)

        UiState.Loading -> NBAOverlayLoadingWheel(
            contentDesc = "Loading",
            modifier = modifier
                .fillMaxWidth()
                .padding(all = NBAPadding.bigger)
        )

        is UiState.Success -> {
            val team = state.data
            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(all = NBAPadding.medium),
                shape = MaterialTheme.shapes.large,
            ) {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(all = NBAPadding.medium),
                ) {
                    Text(
                        text = "${team.fullName}, ${team.abbreviation}",
                        style = MaterialTheme.typography.headlineMedium,
                    )
                    Text(
                        text = team.city
                    )
                    Text(
                        text = team.conference
                    )
                    Text(
                        text = team.division
                    )
                }
            }
        }
    }
}

@DevicePreviews
@Composable
private fun TeamDetailsPreview() {
    NBATheme {
        TeamDetails(
            state = UiState.Success(
                data = MockData.player.team
            )
        )
    }
}
