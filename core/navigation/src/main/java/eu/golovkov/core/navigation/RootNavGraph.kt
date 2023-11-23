package eu.golovkov.core.navigation

import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.ramcosta.composedestinations.spec.Route
import eu.golovkov.feature.playerdetails.PlayerdetailsNavGraph
import eu.golovkov.feature.playerlist.PlayerlistNavGraph
import eu.golovkov.feature.teamdetails.TeamdetailsNavGraph

object RootNavGraph : NavGraphSpec {
    override val destinationsByRoute: Map<String, DestinationSpec<*>> = emptyMap()
    override val route: String = "root"
    override val startRoute: Route = PlayerlistNavGraph
    override val nestedNavGraphs: List<NavGraphSpec> =
        listOf(
            PlayerdetailsNavGraph,
            PlayerlistNavGraph,
            TeamdetailsNavGraph,
        )
}