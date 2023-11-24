package eu.golovkov.feature.playerdetails

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph(start = true)
@Destination
@Composable
fun PlayerDetailsScreen(
    playerId: Int? = null,
    navigator: DestinationsNavigator,
) {

}