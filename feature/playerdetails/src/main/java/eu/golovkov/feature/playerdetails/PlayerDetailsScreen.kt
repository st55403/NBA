package eu.golovkov.feature.playerdetails

import android.util.Log
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph

@RootNavGraph(start = true)
@Destination
@Composable
internal fun PlayerDetailsScreen() {
    Log.v("PlayerDetailsScreen", "PlayerDetailsScreen")
}