package eu.golovkov.feature.playerlist

import android.util.Log
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph

@RootNavGraph(start = true)
@Destination
@Composable
internal fun PlayerListScreen() {
    Log.v("PlayerListScreen", "PlayerListScreen")
}