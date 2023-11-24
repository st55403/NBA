package eu.golovkov.feature.playerdetails

import androidx.compose.foundation.clickable
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
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import eu.golovkov.core.designsystem.component.NBAOverlayLoadingWheel
import eu.golovkov.core.model.data.Team
import eu.golovkov.feature.teamdetails.destinations.TeamDetailsScreenDestination
import org.koin.androidx.compose.getViewModel

@RootNavGraph(start = true)
@Destination
@Composable
fun PlayerDetailsScreen(
    playerId: Int? = null,
    navigator: DestinationsNavigator,
) {
    val viewModel = getViewModel<PlayerDetailsViewModel>()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(playerId) {
        playerId?.let { viewModel.getPlayerDetails(it) }
    }

    PlayerDetails(
        state = state,
        onTeamClick = { teamId ->
            navigator.navigate(TeamDetailsScreenDestination(teamId = teamId.id))
        }
    )
}

@Composable
fun PlayerDetails(
    state: PlayerDetailsUiState,
    onTeamClick: (Team) -> Unit,
) {
    when (state) {
        PlayerDetailsUiState.Error -> {

        }

        PlayerDetailsUiState.Loading -> NBAOverlayLoadingWheel(
            contentDesc = "Loading",
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 32.dp)
        )

        is PlayerDetailsUiState.Success -> {
            val player = state.player
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Text(text = player.firstName)
                Text(text = player.lastName)
                Text(text = player.heightFeet.toString())
                Text(text = player.heightInches.toString())
                Text(text = player.position)
                Text(text = player.weightPounds.toString())
                Text(text = player.firstName)
                Text(text = player.firstName)
                Text(
                    text = player.team.fullName,
                    modifier = Modifier.clickable { onTeamClick(player.team) }
                )
            }
        }
    }
}
