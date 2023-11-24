package eu.golovkov.feature.teamdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import eu.golovkov.core.designsystem.component.NBAOverlayLoadingWheel
import eu.golovkov.core.ui.ErrorState
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
fun TeamDetails(
    modifier: Modifier = Modifier,
    state: TeamUiState,
) {
    when (state) {
        TeamUiState.Error -> ErrorState(modifier)

        TeamUiState.Loading -> NBAOverlayLoadingWheel(
            contentDesc = "Loading",
            modifier = modifier
                .fillMaxWidth()
                .padding(all = 32.dp)
        )

        is TeamUiState.Success -> {
            val team = state.team
            Column(
                modifier = modifier
                    .fillMaxSize()
            ) {
                Text(text = team.abbreviation)
                Text(text = team.city)
                Text(text = team.conference)
                Text(text = team.division)
                Text(text = team.fullName)
                Text(text = team.name)
            }
        }
    }
}
