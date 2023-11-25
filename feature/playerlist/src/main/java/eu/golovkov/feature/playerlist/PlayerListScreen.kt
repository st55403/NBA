package eu.golovkov.feature.playerlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import eu.golovkov.core.designsystem.component.NBALoadingWheel
import eu.golovkov.core.model.data.Player
import eu.golovkov.core.ui.ErrorState
import eu.golovkov.feature.playerdetails.destinations.PlayerDetailsScreenDestination
import org.koin.androidx.compose.getViewModel

@RootNavGraph(start = true)
@Destination
@Composable
fun PlayerListScreen(
    navigator: DestinationsNavigator,
) {
    val viewModel = getViewModel<PlayerListViewModel>()

    val pagingPlayers = viewModel.allPlayers.collectAsLazyPagingItems()

    UsersScreen(
        players = pagingPlayers,
        onPlayerClick = { player ->
            navigator.navigate(PlayerDetailsScreenDestination(playerId = player.id))
        }
    )
}

@Composable
private fun UsersScreen(
    modifier: Modifier = Modifier,
    players: LazyPagingItems<Player>,
    onPlayerClick: (Player) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = if (players.itemCount > 0) Arrangement.spacedBy(8.dp) else Arrangement.Center,
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            bottom = 16.dp,
            top = 16.dp
        ),
    ) {
        when (players.loadState.refresh) {
            LoadState.Loading -> {
                item {
                    NBALoadingWheel(
                        contentDesc = "Loading",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 32.dp)
                    )
                }
            }

            is LoadState.Error -> {
                item {
                    ErrorState(modifier)
                }
            }

            is LoadState.NotLoading -> {
                if (players.itemCount < 1) {
                    item {
                        EmptyItem()
                    }
                }
            }
        }
        items(count = players.itemCount) { index ->
            val item = players[index]
            item?.let {
                PlayerItem(
                    user = item,
                    onClick = { onPlayerClick(item) }
                )
            }
        }
        when (players.loadState.append) {
            LoadState.Loading -> {
                item {
                    NBALoadingWheel(
                        contentDesc = "Loading",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 32.dp)
                    )
                }
            }

            is LoadState.Error -> {
                item {
                    ErrorState(modifier)
                }
            }

            is LoadState.NotLoading -> Unit
        }
    }
}

@Composable
private fun PlayerItem(
    modifier: Modifier = Modifier,
    user: Player,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = MaterialTheme.shapes.large,
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "${user.firstName} ${user.lastName}",
                style = MaterialTheme.typography.headlineMedium,
            )
            Text(
                text = stringResource(R.string.position, user.position),
                style = MaterialTheme.typography.headlineSmall,
            )
            Text(
                text = stringResource(R.string.team, user.team.fullName),
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@Composable
private fun EmptyItem(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(all = 32.dp),
            text = "No user found"
        )
    }
}
