package eu.golovkov.feature.playerlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import eu.golovkov.core.designsystem.component.NBALoadingWheel
import eu.golovkov.core.model.data.Player
import org.koin.androidx.compose.getViewModel

@RootNavGraph(start = true)
@Destination
@Composable
internal fun PlayerListScreen() {
    val viewModel = getViewModel<PlayerListViewModel>()

    val pagingPlayers = viewModel.allPlayers.collectAsLazyPagingItems()

    UsersScreen(
        players = pagingPlayers
    )
}

@Composable
fun UsersScreen(
    modifier: Modifier = Modifier,
    players: LazyPagingItems<Player>
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
                    PaginationErrorItem {
                        players.refresh()
                    }
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
            PlayerItem(user = item)
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
                    PaginationRetryItem {
                        players.retry()
                    }
                }
            }

            is LoadState.NotLoading -> Unit
        }
    }
}

@Composable
fun PlayerItem(
    modifier: Modifier = Modifier,
    user: Player?
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color.Green,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Column {
            Text(
                modifier = modifier.padding(horizontal = 32.dp),
                text = user?.firstName ?: ""
            )
            Text(
                modifier = modifier.padding(horizontal = 32.dp),
                text = user?.lastName ?: ""
            )
            Text(
                modifier = modifier.padding(horizontal = 32.dp),
                text = user?.position ?: ""
            )
            Text(
                modifier = modifier.padding(horizontal = 32.dp),
                text = user?.team?.fullName ?: ""
            )
        }
    }
}

@Composable
fun PaginationErrorItem(
    modifier: Modifier = Modifier,
    onTryAgainClick: () -> Unit
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            modifier = modifier,
            onClick = onTryAgainClick
        ) {
            Text(text = "Try Again")
        }
    }
}

@Composable
fun EmptyItem(
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

@Composable
fun PaginationRetryItem(
    modifier: Modifier = Modifier,
    onRetryClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = 16.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Image(
            modifier = Modifier
                .size(32.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onRetryClick
                ),
            imageVector = Icons.Rounded.Refresh,
            contentDescription = null,
        )
    }
}
