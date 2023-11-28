package eu.golovkov.feature.playerlist

import androidx.compose.foundation.background
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
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import eu.golovkov.core.designsystem.component.NBALoadingWheel
import eu.golovkov.core.designsystem.theme.NBAPadding
import eu.golovkov.core.designsystem.theme.NBATheme
import eu.golovkov.core.model.data.Player
import eu.golovkov.core.ui.DevicePreviews
import eu.golovkov.core.ui.ErrorState
import eu.golovkov.core.ui.MockData
import eu.golovkov.feature.playerdetails.destinations.PlayerDetailsScreenDestination
import kotlinx.coroutines.flow.flowOf
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
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        verticalArrangement = if (players.itemCount > 0) Arrangement.spacedBy(NBAPadding.small) else Arrangement.Center,
        contentPadding = PaddingValues(
            all = NBAPadding.medium
        ),
    ) {
        when (players.loadState.refresh) {
            LoadState.Loading -> {
                item {
                    NBALoadingWheel(
                        contentDesc = "Loading",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = NBAPadding.bigger)
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
                    player = item,
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
                            .padding(all = NBAPadding.bigger)
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
    player: Player,
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
                text = stringResource(R.string.team, player.team.fullName),
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
            modifier = Modifier.padding(all = NBAPadding.bigger),
            text = "No user found"
        )
    }
}

@DevicePreviews
@Composable
private fun PlayerListPreview() {
    NBATheme {
        UsersScreen(
            players = flowOf(
                PagingData.from(
                    listOf(
                        MockData.player,
                        MockData.player,
                        MockData.player,
                    )
                )
            ).collectAsLazyPagingItems(),
            onPlayerClick = { }
        )
    }
}
