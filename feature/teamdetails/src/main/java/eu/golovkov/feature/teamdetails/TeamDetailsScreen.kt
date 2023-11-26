package eu.golovkov.feature.teamdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import eu.golovkov.core.designsystem.component.NBAOverlayLoadingWheel
import eu.golovkov.core.designsystem.theme.NBAPadding
import eu.golovkov.core.designsystem.theme.NBATheme
import eu.golovkov.core.ui.DevicePreviews
import eu.golovkov.core.ui.ErrorState
import eu.golovkov.core.ui.MockData
import org.koin.androidx.compose.getViewModel

@RootNavGraph(start = true)
@Destination
@Composable
fun TeamDetailsScreen(
    teamId: Int? = null,
) {
    val viewModel = getViewModel<TeamDetailsViewModel>()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(teamId) {
        teamId?.let { viewModel.getTeamDetails(it) }
    }

    TeamDetails(
        state = state,
    )
}

@Composable
private fun TeamDetails(
    modifier: Modifier = Modifier,
    state: TeamUiState,
) {
    when (state) {
        TeamUiState.Error -> ErrorState(modifier)

        TeamUiState.Loading -> NBAOverlayLoadingWheel(
            contentDesc = "Loading",
            modifier = modifier
                .fillMaxWidth()
                .padding(all = NBAPadding.bigger)
        )

        is TeamUiState.Success -> {
            val team = state.team
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
            state = TeamUiState.Success(
                team = MockData.player.team
            )
        )
    }
}
