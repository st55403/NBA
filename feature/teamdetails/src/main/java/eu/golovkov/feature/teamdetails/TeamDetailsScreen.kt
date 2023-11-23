package eu.golovkov.feature.teamdetails

import android.util.Log
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph

@RootNavGraph(start = true)
@Destination
@Composable
internal fun TeamDetailsScreen() {
    Log.v("TeamDetailsScreen", "TeamDetailsScreen")
}