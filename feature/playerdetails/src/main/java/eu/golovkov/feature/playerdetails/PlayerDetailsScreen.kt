package eu.golovkov.feature.playerdetails

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import eu.golovkov.core.designsystem.component.NBAOverlayLoadingWheel
import eu.golovkov.core.designsystem.component.NBATopicTag
import eu.golovkov.core.designsystem.theme.NBAPadding
import eu.golovkov.core.designsystem.theme.NBATheme
import eu.golovkov.core.model.data.Player
import eu.golovkov.core.model.data.Team
import eu.golovkov.core.ui.DevicePreviews
import eu.golovkov.core.ui.ErrorState
import eu.golovkov.core.ui.MockData
import eu.golovkov.core.ui.UiState
import eu.golovkov.feature.teamdetails.destinations.TeamDetailsScreenDestination
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.player_details_title),
                        style = MaterialTheme.typography.headlineSmall,
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navigator.popBackStack() },
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
        PlayerDetails(
            modifier = Modifier.padding(paddingValues),
            state = state,
            onTeamClick = { teamId ->
                navigator.navigate(TeamDetailsScreenDestination(teamId = teamId.id))
            }
        )
    }
}

@Composable
private fun PlayerDetails(
    modifier: Modifier = Modifier,
    state: UiState<Player>,
    onTeamClick: (Team) -> Unit,
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
            val player = state.data
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

@DevicePreviews
@Composable
private fun PlayerDetailsPreview() {
    NBATheme {
        PlayerDetails(
            state = UiState.Success(
                data = MockData.player
            ),
            onTeamClick = { /* no-op */ }
        )
    }
}
