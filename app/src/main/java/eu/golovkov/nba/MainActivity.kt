package eu.golovkov.nba

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ramcosta.composedestinations.DestinationsNavHost
import eu.golovkov.core.designsystem.theme.NBATheme
import eu.golovkov.core.navigation.RootNavGraph
import eu.golovkov.core.network.di.NetworkModule
import eu.golovkov.feature.playerdetails.di.PlayerDetailsModule
import eu.golovkov.feature.playerlist.di.PlayerListModule
import eu.golovkov.feature.teamdetails.di.TeamDetailsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startKoin {
            androidContext(this@MainActivity.application)
            modules(
                NetworkModule(),
                PlayerListModule(),
                PlayerDetailsModule(),
                TeamDetailsModule(),
            )
        }

        setContent {
            NBATheme {
                DestinationsNavHost(
                    navGraph = RootNavGraph
                )
            }
        }
    }
}