package eu.golovkov.nba

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import eu.golovkov.core.designsystem.theme.NBATheme
import eu.golovkov.core.navigation.RootNavGraph
import eu.golovkov.core.network.di.NetworkModule
import eu.golovkov.feature.playerdetails.di.PlayerDetailsModule
import eu.golovkov.feature.playerlist.di.PlayerListModule
import eu.golovkov.feature.teamdetails.di.TeamDetailsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalAnimationApi::class)
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
                val engine = rememberAnimatedNavHostEngine(
                    rootDefaultAnimations = createNavGraphAnimations(),
                )
                DestinationsNavHost(
                    navGraph = RootNavGraph,
                    engine = engine,
                )
            }
        }
    }
}

@Composable
private fun createNavGraphAnimations() = RootNavGraphDefaultAnimations(
    enterTransition = {
        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) + fadeIn()
    },
    exitTransition = {
        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left) + fadeOut()
    },
    popEnterTransition = {
        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right) + fadeIn()
    },
    popExitTransition = {
        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) + fadeOut()
    },
)