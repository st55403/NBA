package eu.golovkov.feature.playerdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.getViewModel

@RootNavGraph(start = true)
@Destination
@Composable
fun PlayerDetailsScreen(
    playerId: Int? = null,
    navigator: DestinationsNavigator,
) {
    val viewModel = getViewModel<PlayerDetailsViewModel>()

    LaunchedEffect(playerId) {
        playerId?.let { viewModel.getPlayerDetails(it) }
    }

    val state by viewModel.state.collectAsState()

    PlayerDetails(
        state = state,
    )
}

@Composable
fun PlayerDetails(
    state: PlayerDetailsUiState
) {
    when (state) {
        PlayerDetailsUiState.Error -> {

        }
        PlayerDetailsUiState.Loading -> {

        }
        is PlayerDetailsUiState.Success -> {
            val player = state.player
            Column {
                Text(text = player.firstName)
                Text(text = player.lastName)
                Text(text = player.heightFeet.toString())
                Text(text = player.heightInches.toString())
                Text(text = player.position)
                Text(text = player.weightPounds.toString())
                Text(text = player.firstName)
                Text(text = player.firstName)
                Text(text = player.team.fullName)
            }
        }
    }
}
