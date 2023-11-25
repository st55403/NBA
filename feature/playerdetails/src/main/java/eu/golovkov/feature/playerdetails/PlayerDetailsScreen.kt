package eu.golovkov.feature.playerdetails

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import eu.golovkov.core.designsystem.component.NBAOverlayLoadingWheel
import eu.golovkov.core.designsystem.component.NBATopicTag
import eu.golovkov.core.model.data.Team
import eu.golovkov.core.ui.ErrorState
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
private fun PlayerDetails(
    modifier: Modifier = Modifier,
    state: PlayerDetailsUiState,
    onTeamClick: (Team) -> Unit,
) {
    when (state) {
        PlayerDetailsUiState.Error -> ErrorState(modifier)

        PlayerDetailsUiState.Loading -> NBAOverlayLoadingWheel(
            contentDesc = "Loading",
            modifier = modifier
                .fillMaxWidth()
                .padding(all = 32.dp)
        )

        is PlayerDetailsUiState.Success -> {
            val player = state.player
            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp),
                shape = MaterialTheme.shapes.large,
            ) {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(all = 16.dp),
                ) {
                    Text(
                        text = "${player.firstName} ${player.lastName}",
                        style = MaterialTheme.typography.headlineMedium,
                    )
                    Text(
                        text = stringResource(R.string.position, player.position),
                        style = MaterialTheme.typography.headlineSmall,
                    )
                    Text(
                        text = stringResource(
                            R.string.height,
                            player.heightFeet,
                            player.heightInches
                        ),
                    )
                    Text(
                        text = stringResource(R.string.weight, player.weightPounds),
                    )
                    NBATopicTag(
                        modifier = modifier
                            .align(Alignment.End),
                        followed = true,
                        onClick = { onTeamClick(player.team) },
                        enabled = true,
                        text = {
                            Text(
                                text = player.team.fullName,
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        }
                    )
                }
            }
        }
    }
}
